package com.rmalexander.taskmaster.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.rmalexander.taskmaster.R;

public class TaskDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        Intent taskOriginIntent = getIntent();
        String taskNameString = null;
        if (taskOriginIntent != null) {
            taskNameString = taskOriginIntent.getStringExtra(MyTasksActivity.TASK_TITLE_EXTRA_TAG);
        }

        TextView taskDetailTitleTextView = (TextView) findViewById(R.id.taskDetailActivityTitleTextView);
        if (taskNameString != null) {
            taskDetailTitleTextView.setText(taskNameString);
        }

    }

}