package com.ptpthingers.grpctasks;


import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SyncResult;
import android.os.Bundle;
import android.text.style.TtsSpan;
import android.util.Log;

import com.ptpthingers.yacs5e_app.TTalk;
import com.ptpthingers.yacs5e_app.TUser;
import com.ptpthingers.yacs5e_app.YACS5eGrpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

public class GrpcSyncAdapter extends AbstractThreadedSyncAdapter {
    private String mHost;
    private Integer mPort;
    private String username, password;
    private SharedPreferences connectionSharedPreferences;
    private SharedPreferences accountSharedPreferences;

    public static final String TAG = "SyncAdapter";

    private Context context;


    public GrpcSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        initializeSyncAdapted(context);
    }

    public GrpcSyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        initializeSyncAdapted(context);
    }

    @Override
    public void onPerformSync(Account account,
                              Bundle bundle,
                              String authority,
                              ContentProviderClient contentProviderClient,
                              SyncResult syncResult) {
        initializeSyncAdapted(context);

        ManagedChannel mChannel = ManagedChannelBuilder.forAddress(mHost, mPort).build();
        YACS5eGrpc.YACS5eStub asyncStub = YACS5eGrpc.newStub(mChannel);

        StreamObserver<TTalk> responseStream = CreateResponseStreamObserver(asyncStub);
        StreamObserver<TTalk> requestStream = asyncStub.synchronize(responseStream);

//        TTalk user = TTalk.

//        TUser user = TUser.newBuilder().setLogin("testUser").setPassword("testPass");

        requestStream.onNext(
                TTalk.newBuilder().setUser(TUser.newBuilder()
                        .setLogin("testUser")
                        .setPassword("testPass"))
                        .build());

        requestStream.onCompleted();

    }


    private StreamObserver<TTalk> CreateResponseStreamObserver(YACS5eGrpc.YACS5eStub asyncStub){
        return asyncStub.synchronize(new StreamObserver<TTalk>() {

            @Override
            public void onNext(TTalk value) {
                Log.println(Log.INFO, "ResponseStream:", value.toString());
//                TTalk.UnionCase asd = value.getUnionCase();
//
//
//                switch (asd) {
//                    case USER:
//                }
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onCompleted() {

            }
        });
    }


    private void initializeSyncAdapted(Context context){
//        this.context = context;

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




}

