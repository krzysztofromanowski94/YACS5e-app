package com.ptpthingers.synchronization;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class AuthenticatorService extends Service {

    private AccountAuthenticator  mAuthenticator;

    @Override
    public void onCreate() {
        super.onCreate();
        this.mAuthenticator = new AccountAuthenticator(this);

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mAuthenticator.getIBinder();
    }
}
