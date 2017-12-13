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

import com.google.protobuf.ByteString;
import com.ptpthingers.yacs5e_app.TCharacter;
import com.ptpthingers.yacs5e_app.TTalk;
import com.ptpthingers.yacs5e_app.TUser;
import com.ptpthingers.yacs5e_app.YACS5eGrpc;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

import io.grpc.ManagedChannel;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

/*
* This file is about to be changed. Current state is for testing only.
* Tutorial used: https://github.com/codepath/android_guides/wiki/Server-Synchronization-(SyncAdapter)
*/


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

        // create user credentials message
        TTalk tTalk = TTalk.newBuilder().setUser(TUser.newBuilder()
                .setLogin("testUser")
                .setPassword("testPass"))
                .build();
        // send user credentials message
        requestStream.onNext(tTalk);

        // receive message to confirm logging
        try {
            received = messageQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if ( received == null ) {
            Log.i(TAG, "received is null. Something is very wrong :(");
            return;
        }

        switch (received.getUnionCase()) {
            case GOOD:
                if (!received.getGood()) {
                    Log.i(TAG, "Returning from attempt to login user");
                    // User is not logged in
                    return;
                }
        }
        // User is logged in

        // example character list
        TCharacter[] characterList = new TCharacter[5];

        // 0. even - receive even timestamp from server
        // 1 - newer on client - this.last_sync == server.last_sync && this.last_mod > this.last_sync - that's fine, just update
        // 2 - newer on server - this.last_sync < server.last_sync && last_mod <= last_sync - that's fine, just update
        // 3 - different on client - this.last_sync != server.last_sync && this.last_mod > this.last_sync - generate new uuid, send as new character
        // 4 - not on server - receive timestamp == 0, send complete character
        // 5 - to be deleted - set Character.Delete=true, send to server
        // <6> - not on client - if server have more characters it will send them one-by-one until TTalk.Good=true

        characterList[0] = TCharacter.newBuilder()
                .setBlob(ByteString.copyFromUtf8(""))
                .setLastSync(1513123606)
                .build();

        characterList[1] = TCharacter.newBuilder()
                .setBlob(ByteString.copyFromUtf8(""))
                .setLastSync(1512954576)
                .setLastMod(1512954580)
                .build();

        characterList[2] = TCharacter.newBuilder()
                .setBlob(ByteString.copyFromUtf8(""))
                .setLastSync(1513023228)
                .build();

        characterList[3] = TCharacter.newBuilder()
                .setBlob(ByteString.copyFromUtf8(""))
                .setLastSync(1513023230)
                .build();

        characterList[4] = TCharacter.newBuilder()
                .setBlob(ByteString.copyFromUtf8(""))
                .setLastSync(1513023230)
                .build();


        // example db usage
        CharacterDatabase db = DBInstance.getHook(context);
//        db.characterDao().truncate();
        List<CharacterEntity> characterEntityList = db.characterDao().getAllCharacters();

        if (characterEntityList.size() == 0) {
            Log.i(TAG, "table empty, add entry");

            for (TCharacter tCharacter : characterList) {
                CharacterEntity characterEntity = new CharacterEntity("...");
                characterEntity.setLastSync(tCharacter.getLastSync());

                db.characterDao().insertCharacter(characterEntity);
            }
        }

        Log.i(TAG, db.characterDao().getAllUuid().toString());


        // send characters one-by-one
        // 0. even - receive even timestamp from server
        // 1 - newer on client - this.last_sync == server.last_sync && this.last_mod > this.last_sync - that's fine, just update
        // 2 - newer on server && last_mod <= last_sync - that's fine, just update
        // 3 - different on client - this.last_sync != server.last_sync && this.last_mod > this.last_sync - generate new uuid, send as new character
        // 4 - not on server - receive timestamp == 0, send complete character
        // 5 - to be deleted - set Character.Delete=true, send to server
        // <6> - not on client - if server have more characters it will send them one-by-one until TTalk.Good=true
        for (CharacterEntity characterEntity : db.characterDao().getAllCharacters()) {
            // send message about this character
            requestStream.onNext(
                    TTalk.newBuilder()
                            .setCharacter(TCharacter.newBuilder()
                                    .setLastSync(characterEntity.getLastSync())
                                    .setUuid(characterEntity.getUuid()))
                            .build());

            //receive server parsed character
            try {
                received = messageQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // check if local data needs to be updated

            Long syncDifference = characterEntity.getLastSync() - received.getCharacter().getLastSync();
            Log.i(TAG, "Current character syncDifference: " + syncDifference);

            // 0. even - receive even timestamp from server
            if (syncDifference == 0 && characterEntity.getLastMod() == received.getCharacter().getLastMod()) {
                Log.i(TAG, "Character is even (0) uuid: " + received.getCharacter().getUuid());
            }

            // 1 - newer on client - this.last_sync == server.last_sync && this.last_mod > this.last_sync - that's fine, just update
            else if (syncDifference == 0 && characterEntity.getLastMod() > characterEntity.getLastSync()) {
                Log.i(TAG, "1 - newer on client - this.last_sync == server.last_sync && this.last_mod > this.last_sync uuid: " + received.getCharacter().getUuid());
                // ToDo: generate proper uuid
                String uuid = "111";
                TCharacter newerCharacter = TCharacter.newBuilder()
                        .setUuid(uuid)
                        .setLastSync(System.currentTimeMillis() / 1000L)
                        .setBlob(characterEntity.getDataByteString())
                        .build();
                requestStream.onNext(TTalk.newBuilder().setCharacter(newerCharacter).build());
            }

            // 2 - newer on server && last_mod <= last_sync - that's fine, just update
            else if (syncDifference < 0 && characterEntity.getLastMod() <= characterEntity.getLastSync()) {
                Log.i(TAG, "2 - newer on server && last_mod <= last_sync uuid: " + received.getCharacter().getUuid());
            }

            // 3 - different on client - this.last_sync != server.last_sync && this.last_mod > this.last_sync - generate new uuid, send as new character
            else if (syncDifference != 0 && characterEntity.getLastMod() > characterEntity.getLastSync()) {
                Log.i(TAG, "3 - uuid: " + received.getCharacter().getUuid());
            }


            // 4 - not on server - receive timestamp == 0, send complete character
            else if (received.getCharacter().getLastSync() == 0) {
                Log.i(TAG, "Character not on server (4) uuid: " + characterEntity.getUuid());
                TCharacter newCharacter = TCharacter.newBuilder()
                        .setUuid(characterEntity.getUuid())
                        .setLastSync(characterEntity.getLastSync())
                        .setBlob(characterEntity.getDataByteString())
                        .build();
                requestStream.onNext(TTalk.newBuilder().setCharacter(newCharacter).build());
            }

        }

        requestStream.onNext(TTalk.newBuilder().setGood(true).build());

        Log.i(TAG, "Finished synchronization!");
    }


    private StreamObserver<TTalk> CreateRequestStreamObserver(){
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

    private void initializeSyncAdapted(Context context){
        this.context = context;

        connectionSharedPreferences = context.getSharedPreferences("connection", Context.MODE_PRIVATE);
        mHost = connectionSharedPreferences.getString("mHost", null);
        mPort = connectionSharedPreferences.getInt("mPort", 0);

        accountSharedPreferences = context.getSharedPreferences("account", Context.MODE_PRIVATE);
        username = accountSharedPreferences.getString("username", "");
        password = accountSharedPreferences.getString("password", "");

        messageQueue = new ArrayBlockingQueue<TTalk>(128);
    }
}

