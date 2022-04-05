package com.rmalexander.taskmaster.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.amplifyframework.core.Amplify;
import com.rmalexander.taskmaster.R;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Intent callingIntent = getIntent();
        String email = callingIntent.getStringExtra(VerifyUserActivity.VERIFY_USER_EMAIL_TAG);

        EditText userEmailEditText = (EditText) findViewById(R.id.LoginActivityUserEmailTextPersonName);
        userEmailEditText.setText(email);

        Button loginButton = (Button) findViewById(R.id.LoginActivitySubmitButton);
        loginButton.setOnClickListener(view -> {
            String userEmail = userEmailEditText.getText().toString();
            String password = ((EditText) findViewById(R.id.LoginActivityPasswordTextPassword)).getText().toString();

            Amplify.Auth.signIn(userEmail,
                    password,
                    credentialed -> {
                        Log.i(TAG, "Successfully logged in " + userEmail + ": " + credentialed.toString());
                        Intent goToMyTasksIntent = new Intent(LoginActivity.this, MyTasksActivity.class);
                        startActivity(goToMyTasksIntent);
                    },
                    uncredentialed -> {
                        Log.i(TAG, "Failed to log in user: " + userEmail + ". " + uncredentialed.toString());
                    });
        });

    }


}