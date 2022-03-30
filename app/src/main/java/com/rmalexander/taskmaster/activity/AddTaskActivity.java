package com.rmalexander.taskmaster.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.TaskProgressEnum;
import com.google.android.material.snackbar.Snackbar;
import com.rmalexander.taskmaster.R;

import java.util.Date;

public class AddTaskActivity extends AppCompatActivity {

    public static final String TAG = "AddTaskActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        Spinner taskSpinner = (Spinner) findViewById(R.id.addTaskAddTaskSpinner);
        taskSpinner.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                TaskProgressEnum.values()));

        Button addTaskButton = (Button) findViewById(R.id.addTaskAddTaskButton);

        addTaskButton.setOnClickListener(view ->{
             {
                 String title = ((EditText)findViewById(R.id.addTaskTaskTitleTextEdit)).getText().toString();
                 String description = ((EditText)findViewById(R.id.addTaskTaskDescpritionTextEdit)).getText().toString();
                 String timeStampString = com.amazonaws.util.DateUtils.formatISO8601Date(new Date());

                 Task addedTask = Task.builder()
                         .title(title)
                         .description(description)
                         .dateAdded(new Temporal.DateTime(timeStampString))
                         .progress((TaskProgressEnum) taskSpinner.getSelectedItem())
                         .build();

                 Amplify.API.mutate(
                         ModelMutation.create(addedTask),
                         successResponse -> Log.i(TAG, "AddTaskActivity.onCreate(): successfully added a new task"),
                         failureResponse -> Log.i(TAG, "AddTaskActivity.onCreate(): failed to add new task -- " + failureResponse)
                 );

                 Snackbar.make(findViewById(R.id.addTaskAddTaskButton), "Settings Updated", Snackbar.LENGTH_SHORT).show();
            }
        });

    }
}