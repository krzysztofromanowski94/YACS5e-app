package com.ptpthingers.synchronization;


import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SyncResult;
import android.os.Bundle;
import android.util.Log;

import com.ptpthingers.yacs5e_app.TCharacter;
import com.ptpthingers.yacs5e_app.TTalk;
import com.ptpthingers.yacs5e_app.TUser;
import com.ptpthingers.yacs5e_app.YACS5eGrpc;

import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;

import io.grpc.ManagedChannel;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;


public class GrpcSyncAdapter extends AbstractThreadedSyncAdapter {
    private String mHost;
    private Integer mPort;
    private String username, password;
    private SharedPreferences connectionSharedPreferences;
    private SharedPreferences accountSharedPreferences;

    private static final String TAG = "GrpcSyncAdapter";

    private Context context;
    private final ContentResolver resolver;

    private ArrayBlockingQueue<TTalk> messageQueue;
    private TTalk received;
    private CharacterDatabase db;


    GrpcSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        initializeSyncAdapted(context);
        this.resolver = context.getContentResolver();
    }

    public GrpcSyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        initializeSyncAdapted(context);
        this.resolver = context.getContentResolver();
    }

    @Override
    public void onPerformSync(Account account,
                              Bundle bundle,
                              String authority,
                              ContentProviderClient contentProviderClient,
                              SyncResult syncResult) {

        Log.i(TAG, "Starting synchronization...");

        ManagedChannel mChannel = ManagedChannelSingleton.getManagedChannel(this.context);
        YACS5eGrpc.YACS5eStub asyncStub = YACS5eGrpc.newStub(mChannel);

        StreamObserver<TTalk> requestStream = asyncStub.synchronize(CreateRequestStreamObserver());

        if (accountSharedPreferences.getString("username", "").equals("") || accountSharedPreferences.getString("password", "").equals("")) {
            Log.i(TAG, "Not logged in");
            return;
        }

        // create user credentials message
        TTalk tTalk = TTalk.newBuilder().setUser(TUser.newBuilder()
                .setLogin(accountSharedPreferences.getString("username", ""))
                .setPassword(accountSharedPreferences.getString("password", "")))
                .build();

        // send user credentials message
        requestStream.onNext(tTalk);

        // receive message to confirm logging
        try {
            received = messageQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (received == null) {
            Log.i(TAG, "received is null. Something is very wrong :(");
            Log.i(TAG, "Is signed in?");
            return;
        }

        switch (received.getUnionCase()) {
            case GOOD:
                if (!received.getGood()) {
                    Log.i(TAG, "Not logged in");
                    // User is not logged in
                    return;
                }
                break;
            default:
                Log.i(TAG, "Wrong answer for logging");
                return;
        }
        // User is logged in

        // send characters one-by-one
        // 0. even - receive even timestamp from server
        // 1 - newer on client - this.last_sync == server.last_sync && this.last_mod > this.last_sync - that's fine, just update
        // 2 - newer on server && last_mod <= last_sync - that's fine, just update
        // 3 - different on client - this.last_sync != server.last_sync && this.last_mod > this.last_sync - generate new uuid, send as new character
        // 4 - not on server - receive empty uuid, send complete character
        // 5 - to be deleted - set Character.Delete=true, send to server
        // <6> - not on client - if server have more characters it will send them one-by-one until TTalk.Good=true
        for (CharacterEntity characterEntity : db.characterDao().getAllCharacters(accountSharedPreferences.getString("username", ""))) {
            // send message about this character
            requestStream.onNext(
                    TTalk.newBuilder()
                            .setCharacter(TCharacter.newBuilder()
                                    .setUuid(characterEntity.getUuid())
                                    .setLastSync(characterEntity.getLastSync())
                                    .setLastMod(characterEntity.getLastMod()))
                            .build());

            //receive server parsed character
            try {
                received = messageQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // check if local data needs to be updated
            Long syncDifference = characterEntity.getLastSync() - received.getCharacter().getLastSync();

            // 5 - to be deleted - set Character.Delete=true, send to server
            if (characterEntity.isToDelete()) {
                Log.i(TAG, "5 - Character " + characterEntity.getUuid() + " is to be deleted");
                requestStream.onNext(TTalk.newBuilder()
                        .setCharacter(TCharacter.newBuilder()
                                .setUuid(characterEntity.getUuid())
                                .setDelete(true))
                        .build());
                DBInstance.getHook().characterDao().deleteCharacterEntity(characterEntity.getUuid());
            }

            // 0. even - receive even timestamp from server
            else if (syncDifference == 0 && characterEntity.getLastMod() == received.getCharacter().getLastMod()) {
                Log.i(TAG, "0 - character is even uuid: " + characterEntity.getUuid());
            }

            // 1 - newer on client - this.last_sync == server.last_sync && this.last_mod > this.last_sync - that's fine, just update
            else if (syncDifference == 0 && characterEntity.getLastMod() > characterEntity.getLastSync()) {
                Log.i(TAG, "1 - newer on client uuid: " + characterEntity.getUuid());
                requestStream.onNext(characterEntity.toSyncTTalk());
                db.characterDao().insertCharacter(characterEntity);
            }

            // 2 - newer on server && last_mod <= last_sync - that's fine, just update
            else if (syncDifference < 0 && characterEntity.getLastMod() <= characterEntity.getLastSync()) {
                Log.i(TAG, "2 - newer on server uuid: " + received.getCharacter().getUuid());
                requestStream.onNext(TTalk.newBuilder()
                        .setCharacter(TCharacter.newBuilder()
                                .setUuid(characterEntity.getUuid())
                                .setLastMod(0)
                                .setLastSync(0))
                        .build());

                //receive server parsed character
                try {
                    received = messageQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                TCharacter responseChar = received.getCharacter();
                CharacterEntity charFromServer = new CharacterEntity(
                        responseChar.getUuid(),
                        accountSharedPreferences.getString("username", ""),
                        responseChar.getBlob());
                charFromServer.setLastSync(responseChar.getLastSync());
                charFromServer.setLastMod(responseChar.getLastMod());

                db.characterDao().insertCharacter(charFromServer);

                Log.i(TAG, db.characterDao().getCharacter(characterEntity.getUuid()).toString());
            }

            // 3 - different on client - this.last_sync != server.last_sync && this.last_mod > this.last_sync - generate new uuid, send as new character,
            // sync old character
            else if (!received.getCharacter().getUuid().equals("") && syncDifference != 0 && characterEntity.getLastMod() >= characterEntity.getLastSync()) {
                Log.i(TAG, "3 - different on client this char " + characterEntity.toString());

                // first get server data for this uuid
                requestStream.onNext(TTalk.newBuilder()
                        .setCharacter(TCharacter.newBuilder()
                                .setUuid(characterEntity.getUuid())
                                .setLastMod(0)
                                .setLastSync(0))
                        .build());

                // server data for this uuid
                TTalk charOnServer = TTalk.newBuilder().build();
                try {
                    charOnServer = messageQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // reuse this variable; set new uuid so server will put this into db
                characterEntity.setUuid(UUID.randomUUID().toString());

                // perform all sync steps for new character
                requestStream.onNext(
                        TTalk.newBuilder()
                                .setCharacter(TCharacter.newBuilder()
                                        .setUuid(characterEntity.getUuid())
                                        .setLastSync(characterEntity.getLastSync())
                                        .setLastMod(characterEntity.getLastMod()))
                                .build());

                // we know that sever don't know this uuid, so just empty the messageQueue
                try {
                    received = messageQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // send complete character to be saved on server
                requestStream.onNext(characterEntity.toSyncTTalk());
                db.characterDao().insertCharacter(characterEntity);

                // update character
                characterEntity.setUuid(charOnServer.getCharacter().getUuid());
                characterEntity.setLastSync(charOnServer.getCharacter().getLastSync());
                characterEntity.setLastMod(charOnServer.getCharacter().getLastMod());
                characterEntity.setData(charOnServer.getCharacter().getBlob());

                db.characterDao().insertCharacter(characterEntity);
            }

            // 4 - not on server - receive empty uuid, send complete character
            else if (received.getCharacter().getUuid().equals("")) {
                Log.i(TAG, "Character not on server (4) uuid: " + characterEntity.getUuid());
                requestStream.onNext(characterEntity.toSyncTTalk());
                db.characterDao().insertCharacter(characterEntity);
            } else {
                Log.i(TAG, "Unknown scenario");
                Log.i(TAG, characterEntity.toString());
                Log.i(TAG, received.toString());
            }
        }

        // inform server that there are no more characters on local db
        requestStream.onNext(TTalk.newBuilder().setGood(true).build());

        // server indicates that there are characters to sync by sending TTalk.Good=true;
        // <6> - not on client - if server have more characters it will send them one-by-one until TTalk.Good=true
        boolean receiveNew = false;
        try {
            if (messageQueue.take().getGood()) {
                receiveNew = true;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while (receiveNew) {
            TTalk charReceived = TTalk.newBuilder().build();
            try {
                charReceived = messageQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            switch (charReceived.getUnionCase()) {
                case CHARACTER:
                    CharacterEntity characterEntity = new CharacterEntity(
                            charReceived.getCharacter().getUuid(),
                            accountSharedPreferences.getString("username", ""),
                            charReceived.getCharacter().getBlob());
                    characterEntity.setUuid(charReceived.getCharacter().getUuid());
                    characterEntity.setLastSync(charReceived.getCharacter().getLastSync());
                    characterEntity.setLastMod(charReceived.getCharacter().getLastMod());

                    Log.i(TAG, "To be added to local db:");
                    Log.i(TAG, characterEntity.toString());
                    db.characterDao().insertCharacter(characterEntity);
                    break;

                case GOOD:
                    Log.i(TAG, "No more characters");
                    receiveNew = false;
                    break;

                default:
                    Log.i(TAG, "TTalk receive error. Not predicted type: " + charReceived.getUnionCase().name());
            }
        }

        Log.i(TAG, "Finished synchronization!");
    }


    private StreamObserver<TTalk> CreateRequestStreamObserver() {
        return new StreamObserver<TTalk>() {
            @Override
            public void onNext(TTalk value) {
                try {
                    messageQueue.put(value);
                } catch (InterruptedException e) {
                    Log.i(TAG, "Error putting messageQueue: " + e.getMessage());
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable t) {
                Status status = Status.fromThrowable(t);
                Log.i(TAG, "onError: " + status.toString());
            }

            @Override
            public void onCompleted() {
                Log.i(TAG, "Completed");
            }
        };
    }

    private void initializeSyncAdapted(Context context) {
        this.context = context;

        connectionSharedPreferences = context.getSharedPreferences("connection", Context.MODE_PRIVATE);
        mHost = connectionSharedPreferences.getString("mHost", null);
        mPort = connectionSharedPreferences.getInt("mPort", 0);

        accountSharedPreferences = context.getSharedPreferences("account", Context.MODE_PRIVATE);

        messageQueue = new ArrayBlockingQueue<>(128);
        db = DBInstance.getHook(this.getContext());
    }
}

