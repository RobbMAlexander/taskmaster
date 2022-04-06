package com.rmalexander.taskmaster.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.rmalexander.taskmaster.R;

public class SignUpActivity extends AppCompatActivity {

    public static final String TAG = "SignUpActivity";
    public static final String SIGNUP_EMAIL_ADDRESS_TAG = "Sign_Up_Email_Address_Tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Button signUpButton = (Button) findViewById(R.id.SignUpActivityCredentialsSubmitButton);
        signUpButton.setOnClickListener(view -> {
            String userEmail = ((EditText)findViewById(R.id.SignUpUsernameEntryPersonName)).getText().toString();
            String password = ((EditText)findViewById(R.id.signUpPasswordEntryTextPassword)).getText().toString();

            Amplify.Auth.signUp(userEmail,
                    password,
                    AuthSignUpOptions.builder()
                            .userAttribute(AuthUserAttributeKey.email(), userEmail)
                            .build(),
                    success -> {
                        Log.i(TAG, "Successful account creation: " + success.toString());
                        Intent goToLogInIntent = new Intent(SignUpActivity.this, VerifyUserActivity.class);
                        goToLogInIntent.putExtra(SIGNUP_EMAIL_ADDRESS_TAG, userEmail);
                        startActivity(goToLogInIntent);
                        },
                    failure -> {
                        Log.i(TAG, "Failed to create account: " + userEmail + ". " + failure.toString());
                    }
                    );

        });

    }
}