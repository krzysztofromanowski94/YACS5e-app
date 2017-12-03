package com.ptpthingers.synchronization;


import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.ptpthingers.yacs5e_app.R;

public class SyncActivity extends FragmentActivity {
    // Constants
    // The authority for the sync adapter's content provider
    public static final String AUTHORITY = "com.ptpthingers.syncadapter";
    // An account type, in the form of a domain name
    public static final String ACCOUNT_TYPE = "yacs5e.com";
    // The account name
    public static final String ACCOUNT = "dummyaccount";
    // Instance fields
    Account mAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Create the dummy account
        setContentView(R.layout.sync_activity);
        mAccount = CreateSyncAccount(this);
        Log.i("Constr SyncAct", "Created");
        Log.i("Constr SyncAct", "SyncAccount: " + mAccount.toString());
    }

    public SyncActivity() {
        super();
//        mAccount = CreateSyncAccount(this);
//        Log.i("Constr SyncAct", "Created");
//        Log.i("Constr SyncAct", "SyncAccount: " + mAccount.toString());
    }

    /**
     * Create a new dummy account for the sync adapter
     *
     * @param context The application context
     */
    public static Account CreateSyncAccount(SyncActivity context) {
        // Create the account type and default account
        Account newAccount = new Account(
                ACCOUNT, ACCOUNT_TYPE);
        // Get an instance of the Android account manager
        AccountManager accountManager =
                (AccountManager) context.getSystemService(
                        ACCOUNT_SERVICE);
        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
        if (accountManager.addAccountExplicitly(newAccount, null, null)) {
            /*
             * If you don't set android:syncable="true" in
             * in your <provider> element in the manifest,
             * then call context.setIsSyncable(account, AUTHORITY, 1)
             * here.
             */
        } else {
            /*
             * The account exists or some other error occurred. Log this, report it,
             * or handle it internally.
             */
        }
        return newAccount;
    }

    public void DoSync(){
        // Pass the settings flags by inserting them in a bundle
        Bundle settingsBundle = new Bundle();
        settingsBundle.putBoolean(
                ContentResolver.SYNC_EXTRAS_MANUAL, true);
        settingsBundle.putBoolean(
                ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        /*
         * Request the sync for the default account, authority, and
         * manual sync settings
         */
        Log.i("syncAct.DoSnc CntentRes", mAccount.toString() + settingsBundle.toString());
        ContentResolver.requestSync(mAccount, AUTHORITY, settingsBundle);
    }
}
