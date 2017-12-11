package com.ptpthingers.grpctasks;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.ptpthingers.synchronization.ManagedChannelSingleton;
import com.ptpthingers.yacs5e_app.TUser;
import com.ptpthingers.yacs5e_app.YACS5eGrpc;

import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;


public class GrpcLogin extends AsyncTask<String, Void, GrpcResult> {
    private String mHost;
    private Integer mPort;
    private Context context;
    private SharedPreferences sharedPreferences;

    public GrpcLogin(Context context) {
        super();
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        sharedPreferences = context.getSharedPreferences("connection", Context.MODE_PRIVATE);
        mHost = sharedPreferences.getString("mHost", null);
        mPort = sharedPreferences.getInt("mPort", 0);
    }

    @Override
    protected GrpcResult doInBackground(String... inputParams) {
        if (inputParams.length != 3) {
            return new GrpcResult(false, "Wrong argument count: " + Integer.toString(inputParams.length));
        }

        try {
            String username = inputParams[0];
            String userpass = inputParams[1];

            /* ToDo: In future there should be only one managed channel for application
                    * Singleton?
             */
            ManagedChannel mChannel = ManagedChannelSingleton.getManagedChannel(this.context);

            YACS5eGrpc.YACS5eBlockingStub stub = YACS5eGrpc.newBlockingStub(mChannel);

            // creating message
            TUser user = TUser.newBuilder()
                    .setLogin(username)
                    .setPassword(userpass)
                    .build();

            // send login request. If fail, description message will be returned in catch{} block
            stub.login(user);

            // no StatusRuntimeException so user is signed in
            return new GrpcResult(true, "Successfully signed in!");
        }
        catch (StatusRuntimeException e) {
            return new GrpcResult(false, "Couldn't sign in!\n" + e.getMessage());
        }
    }

    @Override
    protected void onPostExecute(GrpcResult grpcResult) {
        super.onPostExecute(grpcResult);
    }
}