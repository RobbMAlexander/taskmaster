package com.rmalexander.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SettingsActivity extends AppCompatActivity {

    SharedPreferences userPreferences;
    public static final String USERNAME_TAG = "username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        userPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String username = userPreferences.getString(USERNAME_TAG, "");
        if (!username.isEmpty()){
            EditText usernameEditText = (EditText) findViewById(R.id.settingsUsernameEntryEditText);
            usernameEditText.setText(username);
        }

        Button settingsUpdateButton = findViewById(R.id.settingsUsernameSubmitButton);
        settingsUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor preferencesEditor = userPreferences.edit();
                EditText usernameEditText = (EditText) findViewById(R.id.settingsUsernameEntryEditText);
                String usernameString = usernameEditText.getText().toString();
                preferencesEditor.putString(USERNAME_TAG, usernameString);
                preferencesEditor.apply();
            }
        });

    }
}