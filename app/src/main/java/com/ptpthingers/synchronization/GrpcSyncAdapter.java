package com.ptpthingers.synchronization;


import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SyncResult;
import android.os.Bundle;
import android.text.style.TtsSpan;
import android.util.Log;

import com.google.protobuf.ByteString;
import com.ptpthingers.synchronization.GeneralAccount;
import com.ptpthingers.yacs5e_app.TCharacter;
import com.ptpthingers.yacs5e_app.TTalk;
import com.ptpthingers.yacs5e_app.TUser;
import com.ptpthingers.yacs5e_app.YACS5eGrpc;

import java.util.logging.Level;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
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

    public static final String TAG = "GrpcSyncAdapter";

    private Context context;
    private final ContentResolver resolver;



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
//        initializeSyncAdapted(context);

        Log.i(TAG, "Starting synchronization...");



//        ManagedChannel mChannel = ManagedChannelBuilder.forAddress(mHost, mPort).build();
        ManagedChannel mChannel = ManagedChannelSingleton.getManagedChannel(this.context);
        YACS5eGrpc.YACS5eStub asyncStub = YACS5eGrpc.newStub(mChannel);

        StreamObserver<TTalk> responseStream = CreateResponseStreamObserver(asyncStub);
//        StreamObserver<TTalk> requestStream = asyncStub.synchronize(responseStream);

//        TTalk user = TTalk.

//        TUser user = TUser.newBuilder().setLogin("testUser").setPassword("testPass");

        TTalk tTalk = TTalk.newBuilder().setUser(TUser.newBuilder()
                .setLogin("testUser")
                .setPassword("testPass"))
                .build();


        responseStream.onNext(tTalk);

        TTalk tCharacter = TTalk.newBuilder().setCharacter(TCharacter.newBuilder()
        .setBlob(ByteString.copyFromUtf8("\"asdasd\""))
        .setTimestamp(123)
        .setUuid(ByteString.copyFromUtf8("123123123"))).build();

        responseStream.onNext(tCharacter);

//        responseStream.onNext();

//        requestStream.onNext(tTalk);

//        requestStream.onCompleted();

        Log.i(TAG, "Finished synchronization!");

    }


    private StreamObserver<TTalk> CreateResponseStreamObserver(YACS5eGrpc.YACS5eStub asyncStub){
        return asyncStub.synchronize(new StreamObserver<TTalk>() {

            @Override
            public void onNext(TTalk value) {
                Log.i(TAG, "ResponseStream: " + value.toString());
//                TTalk.UnionCase asd = value.getUnionCase();
//
//
//                switch (asd) {
//                    case USER:
//                }
            }

            @Override
            public void onError(Throwable t) {
                Status status = Status.fromThrowable(t);
                Log.i(TAG, "onError: " + status.toString());


            }

            @Override
            public void onCompleted() {

            }
        });
    }


    private void initializeSyncAdapted(Context context){
        this.context = context;

        connectionSharedPreferences = context.getSharedPreferences("connection", Context.MODE_PRIVATE);
        mHost = connectionSharedPreferences.getString("mHost", null);
        mPort = connectionSharedPreferences.getInt("mPort", 0);

        accountSharedPreferences = context.getSharedPreferences("account", Context.MODE_PRIVATE);
        username = accountSharedPreferences.getString("username", "");
        password = accountSharedPreferences.getString("password", "");



    }


//    private ManagedChannel openConnection(){
//
//    }




//    public static void performSync() {
//        Log.i(TAG, "performSync ask for sync");
//        Bundle b = new Bundle();
//        b.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
////        b.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
//        Log.i(TAG, GeneralAccount.getAccount().toString() + " " + b.toString() );
////        ContentResolver.
//        ContentResolver.requestSync(GeneralAccount.getAccount(),
//                "com.ptpthingers.yacs5e", b);
//        Log.i(TAG, "performSync finished");
//    }
}

