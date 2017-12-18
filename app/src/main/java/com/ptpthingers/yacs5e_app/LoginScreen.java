package com.ptpthingers.yacs5e_app;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ptpthingers.grpctasks.GrpcLogin;
import com.ptpthingers.grpctasks.GrpcRegister;
import com.ptpthingers.grpctasks.GrpcResult;

import java.util.concurrent.ExecutionException;


public class LoginScreen extends AppCompatActivity {

    private Button mLoginButton, mRegisterButton;
    private EditText mLoginText, mPassText;
    private SharedPreferences accountSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        mLoginButton = (Button) findViewById(R.id.login_button);
        mRegisterButton = (Button) findViewById(R.id.register_button);
        mLoginText = (EditText) findViewById(R.id.login_username);
        mPassText = (EditText) findViewById(R.id.login_password);

        mLoginButton.setOnClickListener(mLoginListener);
        mRegisterButton.setOnClickListener(mRegisterListener);
        accountSharedPreferences = this.getSharedPreferences("account", Context.MODE_PRIVATE);
    }

    private boolean validate() {
        if(mLoginText.getText().toString().isEmpty() || mPassText.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "You need to fill in the fields!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private View.OnClickListener mLoginListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!validate()) return;

            GrpcResult result;
            try {
                result = new GrpcLogin(getApplicationContext()).execute(
                        mLoginText.getText().toString(),
                        mPassText.getText().toString(),
                        "token")
                        .get();
            } catch (InterruptedException | ExecutionException e) {
                result = new GrpcResult(false, "Something very wrong happened.");
                e.printStackTrace();
            }

            if (result.isOk()) {
                Toast.makeText(getApplicationContext(), "Successfully signed user " + mLoginText.getText().toString(), Toast.LENGTH_SHORT).show();
                accountSharedPreferences.edit().putString("username", mLoginText.getText().toString()).apply();
                accountSharedPreferences.edit().putString("password", mPassText.getText().toString()).apply();
            }
            else {
                Toast.makeText(getApplicationContext(), "Error logging in...", Toast.LENGTH_SHORT).show();
            }

        }
    };

    private View.OnClickListener mRegisterListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (!validate()) return;

            GrpcResult result;
            try {
                result = new GrpcRegister(getApplicationContext()).execute(
                        mLoginText.getText().toString(),
                        mPassText.getText().toString(),
                        "token")
                        .get();
            } catch (InterruptedException | ExecutionException e) {
                result = new GrpcResult(false, "Something very wrong happened.");
                e.printStackTrace();
            }
            if (result.isOk()) {
                Toast.makeText(getApplicationContext(), "Successfully registered user " + mLoginText.getText().toString(), Toast.LENGTH_SHORT).show();
                accountSharedPreferences.edit().putString("username", mLoginText.getText().toString()).apply();
                accountSharedPreferences.edit().putString("password", mPassText.getText().toString()).apply();
            } else {
                Toast.makeText(getApplicationContext(), "Error registering...", Toast.LENGTH_SHORT).show();
            }

        }
    };
}
