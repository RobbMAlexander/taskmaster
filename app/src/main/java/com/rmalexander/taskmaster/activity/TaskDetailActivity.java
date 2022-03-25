package com.rmalexander.taskmaster.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.rmalexander.taskmaster.R;
import com.rmalexander.taskmaster.database.TaskMasterDatabase;

public class TaskDetailActivity extends AppCompatActivity {

    TaskMasterDatabase taskMasterDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        Intent taskOriginIntent = getIntent();
        String taskTitleString = null;
        String taskBodyString = null;
        long taskId = 0;

        if (taskOriginIntent != null) {
            taskId = taskOriginIntent.getLongExtra(MyTasksActivity.TASK_ID_EXTRA_TAG, 0);

            //taskTitleString = taskOriginIntent.getStringExtra(MyTasksActivity.TASK_TITLE_EXTRA_TAG);
            taskTitleString = taskMasterDatabase.taskDao().findById(taskId).getTitle();

            taskBodyString = taskMasterDatabase.taskDao().findById(taskId).getBody();
        }

        TextView taskDetailTitleTextView = (TextView) findViewById(R.id.taskDetailActivityTitleTextView);
        if (taskTitleString != null) {
            taskDetailTitleTextView.setText(taskTitleString);
        }

        TextView taskDetailTaskBodyTextView = (TextView) findViewById(R.id.taskDetailTaskBodyTextView);
        if (taskBodyString != null) {
            taskDetailTaskBodyTextView.setText(taskBodyString);
        }

    }

}