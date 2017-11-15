package com.ptpthingers.yacs5e_app;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.TimeUnit;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

public class LoginScreen extends AppCompatActivity {

    private Button mSendButton;
    private EditText mLoginText, mPassText;
    private TextView mResultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        mSendButton = (Button) findViewById(R.id.login_button);
        mLoginText = (EditText) findViewById(R.id.login_text);
        mPassText = (EditText) findViewById(R.id.pass_text);
        mResultText = (TextView) findViewById(R.id.result_text);
        mResultText.setMovementMethod(new ScrollingMovementMethod());

        mSendButton.setOnClickListener(mLoginListener);

    }

    private View.OnClickListener mLoginListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new GrpcTask().execute();
        }
    };

    private class GrpcTask extends AsyncTask<Void, Void, String> {
        private String mHost;
        private int mPort;
        private ManagedChannel mChannel;
        private SharedPreferences sp;


        @Override
        protected void onPreExecute() {

            //these values work only for test purposes
            mHost = "18.216.25.170";
            mPort = 13334;
            if(mLoginText.getText().toString().isEmpty() || mPassText.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "You need to fill in the fields!", Toast.LENGTH_SHORT).show();
                this.cancel(false);
            }
        }

        @Override
        protected String doInBackground(Void... nothing) {
            try {
                // creating comm channel
                mChannel = ManagedChannelBuilder.forAddress(mHost, mPort).usePlaintext(true).build();
                // creating blocking stub for sync
                YACS5eGrpc.YACS5eBlockingStub stub = YACS5eGrpc.newBlockingStub(mChannel);

                // creating message
                TUser user = TUser.newBuilder()
                        .setLogin(mLoginText.getText().toString())
                        .setPassword(mPassText.getText().toString())
                        .build();
                // saving a reply
                Empty reply = stub.login(user);
                return reply.toString();

            } catch (StatusRuntimeException e) {
                String staCode = e.getStatus().getDescription();
                return "Couldn't sign in!\n" + staCode;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                mChannel.shutdown().awaitTermination(1, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            mResultText.setText(result);
        }
    }
}
