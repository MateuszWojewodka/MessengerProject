package com.example.sobiech.messenger;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Modules.AuthenticationModule;

import static android.Manifest.permission.READ_CONTACTS;

public class LoginActivity extends AppCompatActivity{

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
                if (authenticationModule.logUserIn(mUserNameView.getText().toString(), mPasswordView.getText().toString())) {
                    userName = mUserNameView.getText().toString();
                    Intent intent = new Intent (LoginActivity.this, MenuActivity.class);
                    startActivityForResult(intent, 0);
                    finish();
                }
                else {
                    Intent intent = new Intent (LoginActivity.this, MenuActivity.class);
                    startActivityForResult(intent, 0);
                    finish();
                    Toast.makeText(getApplicationContext(),MESSAGE_LOGIN_ERROR,Toast.LENGTH_SHORT).show();
                }
            }
        });

        btRegister.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (authenticationModule.registerUser(mUserNameView.getText().toString(), mPasswordView.getText().toString())) {
                    Toast.makeText(getApplicationContext(),MESSAGE_REGISTRATION_SUCESSFULLY,Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(),MESSAGE_REGISTRATION_ERROR,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

