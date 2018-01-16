package com.example.sobiech.messenger;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.AsyncTask;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import Modules.AuthenticationModule;

public class LoginRegisterActivity extends AppCompatActivity{

    private static final String MESSAGE_LOGIN_ERROR = "Niepoprawny login lub hasło";
    private static final String MESSAGE_REGISTRATION_SUCESSFULLY = "Pomyślnie zarejestrowano";
    private static final String MESSAGE_REGISTRATION_ERROR = "Nie udało się zarejestrować";

    private EditText mUserNameView;
    private EditText mPasswordView;

    private AuthenticationModule authenticationModule;
    public static String userName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        authenticationModule = AuthenticationModule.getInstance();

        setContentView(R.layout.activity_login);

        mUserNameView = findViewById(R.id.etUserName);
        mPasswordView = findViewById(R.id.etPassword);
        Button btSignIn = findViewById(R.id.btSignIn);
        Button btRegister = findViewById(R.id.btRegister);

        btSignIn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                tryLogUserIn(mUserNameView.getText().toString(), mPasswordView.getText().toString());
            }
        });

        btRegister.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                tryRegisterUser(mUserNameView.getText().toString(), mPasswordView.getText().toString());
            }
        });
    }

    private void tryRegisterUser(String userName, String password) {

        class RegisterUserAsyncTask extends AsyncTask<String, Integer, Boolean> {

            @Override
            protected Boolean doInBackground(String... strings) {
                return authenticationModule.registerUser(strings[0], strings[1]);
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);

                if (aBoolean)
                    Toast.makeText(getApplicationContext(),MESSAGE_REGISTRATION_SUCESSFULLY,Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),MESSAGE_REGISTRATION_ERROR,Toast.LENGTH_SHORT).show();
            }
        }

        new RegisterUserAsyncTask().execute(userName, password);
    }

    private void tryLogUserIn(String userName, String password) {

        class LogUserInAsyncTask extends AsyncTask<String, Integer, Boolean> {

            @Override
            protected Boolean doInBackground(String... strings) {
                return authenticationModule.logUserIn(strings[0], strings[1]);
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);

                if (aBoolean){
                    Intent intent = new Intent (LoginRegisterActivity.this, MenuActivity.class);
                    startActivityForResult(intent, 0);
                    finish();
                }
                else
                    Toast.makeText(getApplicationContext(),MESSAGE_LOGIN_ERROR,Toast.LENGTH_SHORT).show();
            }
        }

        new LogUserInAsyncTask().execute(userName, password);
    }
}

