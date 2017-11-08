package yacs5e.ptpthingers.com.yacs5e_app;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.TimeUnit;

import io.grpc.Grpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class LoginTest extends AppCompatActivity {

    private Button mSendButton;
    private TextView mResultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_test);

        mSendButton = (Button) findViewById(R.id.send_button);
        mResultText = (TextView) findViewById(R.id.rpcText);
    }

    public void sendRPCRequest(android.view.View view) {
        new GrpcTask().execute();
    }

    private class GrpcTask extends AsyncTask<Void, Void, String> {
        private String mHost;
        private int mPort;
        private String username;
        private String userpass;
        private ManagedChannel mChannel;


        @Override
        protected void onPreExecute() {

            //these vaues work only for test purposes
            mHost = "18.216.25.170";
            mPort = 13334;
            username = "testUser";
            userpass = "testPass";
            mResultText.setText("");
        }

        @Override
        protected String doInBackground(Void... nothing) {
            try {
                // creating comm channel
                mChannel = ManagedChannelBuilder.forAddress(mHost, mPort).usePlaintext(true).build();
                // creating blocking stub for sync
                YACS5eGrpc.YACS5eBlockingStub stub = YACS5eGrpc.newBlockingStub(mChannel);

                // creating message
                TUser user = TUser.newBuilder().setLogin(username).setPassword(userpass).build();
                // saving a reply
                Empty reply = stub.login(user);

                return reply.toString() + "\n\n That means it's working ;)";
            } catch (Exception e) {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                pw.flush();
                return String.format("Failed... : %n%s", sw);
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
