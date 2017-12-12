package com.ptpthingers.synchronization;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class SyncService extends Service {
    /**
     * Lock use to synchronize instantiation of SyncAdapter.
     */
    private static final Object LOCK = new Object();
    private static GrpcSyncAdapter grpcSyncAdapter;


    @Override
    public void onCreate() {
        // GrpcSyncAdapter is not Thread-safe
        synchronized (LOCK) {
            // Instantiate our GrpcSyncAdapter
            grpcSyncAdapter = new GrpcSyncAdapter(this, false);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return grpcSyncAdapter.getSyncAdapterBinder();
    }
}