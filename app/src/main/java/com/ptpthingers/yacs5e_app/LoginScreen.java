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
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import com.ptpthingers.grpctasks.GrpcLogin;
import com.ptpthingers.grpctasks.GrpcResult;


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

        mLoginText.setText("testUser");
        mPassText.setText("testPass");

    }

    private View.OnClickListener mLoginListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(mLoginText.getText().toString().isEmpty() || mPassText.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "You need to fill in the fields!", Toast.LENGTH_SHORT).show();
                return;
            }

            GrpcResult result;
            try {
                result = new GrpcLogin(getApplicationContext()).execute(
                        mLoginText.getText().toString(),
                        mPassText.getText().toString(),
                        "token")
                        .get();
            } catch (InterruptedException | ExecutionException e) {
                // ToDo: send email with stacktrace if this happens
                result = new GrpcResult(false, "Something very wrong happened.");
                e.printStackTrace();
            }
            Toast.makeText(getApplicationContext(), result.toString(), Toast.LENGTH_SHORT).show();

//            mResultText.setText(result.toString());
        }
    };
}
