package com.rmalexander.taskmaster.activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.Team;
import com.rmalexander.taskmaster.R;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class TaskDetailActivity extends AppCompatActivity {

    public static final String TAG = "TaskDetailActivity";

    private Task displayedTask = null;
    private CompletableFuture<Task> taskCompletableFuture = null;
    //TODO: add Task type field -- string or int with associated icon
    //private Spinner taskTypeSpinner = null;
    private Spinner progressSpinner = null;
    private EditText titleEditText;
    private EditText descriptionEditText;

    ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        Intent taskOriginIntent = getIntent();
        String taskTitleString = null;
        String taskBodyString = null;
        String taskId = "";
        taskCompletableFuture = new CompletableFuture<>();

        if (taskOriginIntent != null) {
            taskId = taskOriginIntent.getStringExtra(MyTasksActivity.TASK_ID_EXTRA_TAG);
        }

        String intermediaryId = taskId;

        // Query to get fields for provided task ID
        Amplify.API.query(
                ModelQuery.list(Task.class),
                success -> {
                    Log.i(TAG, "Successfully loaded Task list");

                    for(Task savedTask : success.getData()){
                        if (savedTask.getId().equals(intermediaryId)){
                            taskCompletableFuture.complete(savedTask);
                        }
                    }
                },
                failure -> {
                    Log.i(TAG, "Failed to load Task list");
                }
        );

        TextView taskDetailTitleTextView = (TextView) findViewById(R.id.taskDetailActivityTitleTextView);
        TextView taskDetailTaskBodyTextView = (TextView) findViewById(R.id.taskDetailTaskBodyTextView);

        try {
            displayedTask = taskCompletableFuture.get();
            taskDetailTitleTextView.setText(displayedTask.getTitle());
            taskDetailTaskBodyTextView.setText(displayedTask.getDescription());
        } catch (InterruptedException interruptedException) {
            Log.e(TAG, "InterruptedException occurred during: taskCompletableFuture.get()");
            Thread.currentThread().interrupt();
        } catch (ExecutionException executionException) {
            Log.e(TAG, "ExecutionException occurred during: taskCompletableFuture.get()");
        }

        // TODO add s3, come back
       /*String s3Key = displayedTask.getTaskImageS3key();
        if (s3Key != null && !s3Key.isEmpty()){

        }*/


    }

    private void startImageSelectionIntent() {
        Intent imageFileSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
        imageFileSelectionIntent.setType("*/*");
        imageFileSelectionIntent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"image/jpeg", "image/png"});
    }

    private void getImageSelectionActivityResultLauncher(){
        ActivityResultLauncher<Intent> imageSelectionActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK){
                            if (result.getData() != null) {
                               Uri selectedImageUri = result.getData().getData();

                            }
                        } else {
                            Log.e(TAG,"Activity result error in ActivityResultLauncher.onActivityResult");
                        }
                    }
                }
        );
    }

}