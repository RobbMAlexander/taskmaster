package com.rmalexander.taskmaster.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.amplifyframework.core.Amplify;
import com.rmalexander.taskmaster.R;


public class VerifyUserActivity extends AppCompatActivity {

    public static final String TAG = "VerifyUserActivity";
    public static final String VERIFY_USER_EMAIL_TAG = "Verify_User_Email_Tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_user);

        Intent callingIntent = getIntent();
        String email = callingIntent.getStringExtra(SignUpActivity.SIGNUP_EMAIL_ADDRESS_TAG);
        EditText userEmailEditText = (EditText) findViewById(R.id.VerifyUserEmailEntryEditText);
        userEmailEditText.setText(email);

        Button verifyUserSubmitButton = (Button) findViewById(R.id.VerifyUserSubmitButton);
        verifyUserSubmitButton.setOnClickListener(view -> {
            String userEmail = userEmailEditText.getText().toString();
            String emailVerificationCode = ((EditText) findViewById(R.id.VerifyUserVerificationCodeEntry)).getText().toString();

            Amplify.Auth.confirmSignUp(userEmail,
                    emailVerificationCode,
                    verified -> {
                        Log.i(TAG, "Successfully verified account: " + verified.toString());
                        Intent goToLogInIntent = new Intent(VerifyUserActivity.this, LoginActivity.class);
                        goToLogInIntent.putExtra(VERIFY_USER_EMAIL_TAG, userEmail);
                        startActivity(goToLogInIntent);
                    },
                    unverified -> {
                        Log.i(TAG, "Failed to verify account: " + email + ". " + unverified.toString());
                    }
            );
        });
    }
}