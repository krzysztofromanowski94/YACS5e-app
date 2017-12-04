package com.ptpthingers.synchronization;


import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class ManagedChannelSingleton {

    private static ManagedChannel managedChannel;
    private static SharedPreferences connectionSharedPreferences;

    public static ManagedChannel getManagedChannel(Context context){
        if (managedChannel == null) {
            connectionSharedPreferences = context.getSharedPreferences("connection", Context.MODE_PRIVATE);
            String mHost = connectionSharedPreferences.getString("mHost", null);
            Integer mPort = connectionSharedPreferences.getInt("mPort", 0);
            managedChannel = ManagedChannelBuilder.forAddress(mHost, mPort).build();
        }
        return managedChannel;
    }
}
