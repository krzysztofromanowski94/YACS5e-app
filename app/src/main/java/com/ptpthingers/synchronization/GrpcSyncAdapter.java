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



    public GrpcSyncAdapter(Context context, boolean autoInitialize) {
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

        TCharacter[] characterList = new TCharacter[2];

        characterList[0] = TCharacter.newBuilder()
                .setBlob(ByteString.copyFromUtf8(""))
                .setTimestamp(1512954398)
                .setUuid("1234567891234567").build();

        characterList[1] = TCharacter.newBuilder()
                .setBlob(ByteString.copyFromUtf8(""))
                .setTimestamp(1512954574)
                .setUuid("1234567891234568").build();

        // send characters one-by-one
        for (TCharacter tCharacter : characterList) {
            // send message about this character
            requestStream.onNext(TTalk.newBuilder().setCharacter(tCharacter).build());

//          receive server parsed character
            try {
                received = messageQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // check if local data needs to be updated

            Long timestampDifference = received.getCharacter().getTimestamp() - tCharacter.getTimestamp();

            if (timestampDifference > 0) {
                Log.i(TAG, "Timestamp is older on client");
            }
            else if (timestampDifference == 0) {
                Log.i(TAG, "Timestamp is equal");
            }
            else if (timestampDifference < 0) {
                Log.i(TAG, "Timestamp older on server");
            }

        }

        Log.i(TAG, "Finished synchronization!");

    }


    private StreamObserver<TTalk> CreateRequestStreamObserver(){
        return new StreamObserver<TTalk>() {
            @Override
            public void onNext(TTalk value) {
                try {
                    Log.i(TAG, "Putting to messageQueue...");
//                    received = value;
                    messageQueue.put(value);
                    Log.i(TAG, "Putted to messageQueue...");
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

    private class ReceivedMessage  {
        private TTalk ttalk;
        private boolean LOCK;

        public ReceivedMessage() {
            super();
            LOCK = false;
        }
    }

}

