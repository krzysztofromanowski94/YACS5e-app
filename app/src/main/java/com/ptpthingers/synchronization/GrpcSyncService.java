package com.ptpthingers.synchronization;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.ptpthingers.grpctasks.GrpcSyncAdapter;

public class GrpcSyncService extends Service{

    private static GrpcSyncAdapter grpcSyncAdapter;
    private static final Object grpcSyncAdapterLock = new Object();

    @Override
    public void onCreate() {
        super.onCreate();

        synchronized (grpcSyncAdapterLock) {
            if (grpcSyncAdapter == null) {
                grpcSyncAdapter = new GrpcSyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return grpcSyncAdapter.getSyncAdapterBinder();
    }
}
