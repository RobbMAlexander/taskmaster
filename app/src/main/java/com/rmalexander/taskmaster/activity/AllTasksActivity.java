package com.rmalexander.taskmaster.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.amplifyframework.core.Amplify;
import com.rmalexander.taskmaster.R;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AllTasksActivity extends AppCompatActivity {

    public final String TAG = "AllTasksActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tasks);

        String testFileName = "testFileName";
        File testFile = new File(getApplicationContext().getFilesDir(), testFileName);

        try {
            BufferedWriter testFileBufferedWriter = new BufferedWriter(new FileWriter(testFile));
            testFileBufferedWriter.append("This is practice text\nWith formatting tests");
            testFileBufferedWriter.close();
        } catch (IOException ioException) {
            Log.e(TAG, "Failed to write file: " + testFileName);
        }

        String testFileS3Key = "testS3file.txt";

        Amplify.Storage.uploadFile(
                testFileS3Key,
                testFile,
                success -> {
                    Log.i(TAG, "Successfully uploaded file with key: " + success.getKey());
                },
                failure -> {
                    Log.i(TAG, "Failed to upload file: " + testFileName);
                }
        );
    }
}