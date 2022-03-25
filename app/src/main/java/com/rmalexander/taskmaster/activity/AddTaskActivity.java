package com.rmalexander.taskmaster.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.rmalexander.taskmaster.R;
import com.rmalexander.taskmaster.database.TaskMasterDatabase;
import com.rmalexander.taskmaster.model.Task;

import java.util.Date;

public class AddTaskActivity extends AppCompatActivity {

    TaskMasterDatabase taskMasterDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        taskMasterDatabase = Room.databaseBuilder(
                getApplicationContext(),
                TaskMasterDatabase.class,
                "rmalexander_taskmaster")
                .allowMainThreadQueries() //NOT FOR REAL-WORLD APPLICATIONS!
                .build();

        Spinner taskSpinner = (Spinner) findViewById(R.id.addTaskAddTaskSpinner);
        taskSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                Task.TaskStateEnum.values()));

        Button addTaskButton = (Button) findViewById(R.id.addTaskAddTaskButton);

        addTaskButton.setOnClickListener(view ->{
             {
                Task addedTask = new Task(
                        ((EditText) findViewById(R.id.addTaskTaskTitleTextEdit)).getText().toString(),
                        ((EditText) findViewById(R.id.addTaskTaskDescpritionTextEdit)).getText().toString(),
                        Task.TaskStateEnum.valueOf(taskSpinner.getSelectedItem().toString())
            );
                taskMasterDatabase.taskDao().insertTask(addedTask);
                //TODO: add toast/snackbar for text confirmation
                 Snackbar.make(findViewById(R.id.addTaskAddTaskButton), "Settings Updated", Snackbar.LENGTH_SHORT).show();
                //((TextView) findViewById(R.id.addTaskSubmitTextView)).setText(R.string.task_added);
            }
        });

    }
}