package com.ptpthingers.yacs5e_app;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ptpthingers.grpctasks.GrpcSyncAdapter;
import com.ptpthingers.synchronization.GeneralAccount;
//import com.ptpthingers.synchronization.SyncActivity;
import com.ptpthingers.synchronization.SyncAdapter;

/**
 * Created by kromanow on 03.12.2017.
 */

public class SyncScreen extends AppCompatActivity {
    Button mSyncButton;

//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View rootView = inflater.inflate(R.layout.sync_activity, container, false);
//
//        mSyncButton = (Button) rootView.findViewById(R.id.sync_button);
//
//        mSyncButton.setOnClickListener(mSyncListener);
//
//
//        return rootView;
//    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.sync_activity);

        mSyncButton = (Button) findViewById(R.id.sync_button);
        Log.i("SyncScreen onCreate", "Initialized button");
        mSyncButton.setOnClickListener(mSyncListener);


        // Create your sync account
        GeneralAccount.createSyncAccount(this);

    }

    private View.OnClickListener mSyncListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.i("dupa", "run onClick");
            // Perform a manual sync by calling this:
//            SyncAdapter.performSync();
//            GrpcSyncAdapter.per
//            new SyncActivity().DoSync();
            Log.i("dupa", "finished onClick");

        }
    };
}
