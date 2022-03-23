package com.rmalexander.taskmaster;

import static com.rmalexander.taskmaster.SettingsActivity.USERNAME_TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MyTasksActivity extends AppCompatActivity {

    SharedPreferences userPreferences;
    public static final String TASK_NAME_TAG = "taskName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tasks);

        userPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String username = userPreferences.getString(USERNAME_TAG, "");
        if (!username.equals("")) {
            TextView userTasksTitle = (TextView) findViewById(R.id.myTasksTitleTextView);
            String userTasksTitleText = username + "\'s Tasks";
            userTasksTitle.setText(userTasksTitleText);

        }

        wireSettingsButton();
        wireTaskButtons();
        wireAddTaskButton();
        wireAllTasksButton();

    }

    private void wireAddTaskButton (){
        Button addTaskButton = (Button) findViewById(R.id.myTasksAddTaskButton);

        addTaskButton.setOnClickListener(view ->
                {
                    Intent goToSettingsIntent = new Intent(MyTasksActivity.this, AddTaskActivity.class);
                    startActivity(goToSettingsIntent);
                }
        );
    }

    private void wireAllTasksButton (){
        Button allTasksButton = (Button) findViewById(R.id.myTasksAllTasksButton);

        allTasksButton.setOnClickListener(view ->
                {
                    Intent goToSettingsIntent = new Intent(MyTasksActivity.this, AllTasksActivity.class);
                    startActivity(goToSettingsIntent);
                }
        );
    }

    private void wireSettingsButton() {
        ImageButton settingsButton = (ImageButton) findViewById(R.id.myTasksSettingsButton);

        settingsButton.setOnClickListener(view ->
                {
                    Intent goToSettingsIntent = new Intent(MyTasksActivity.this, SettingsActivity.class);
                    startActivity(goToSettingsIntent);
                }
        );

    }

    private void wireTaskButtons() {
        Button [] taskButtonsArr = new Button[3];
        Button task1Button = (Button) findViewById(R.id.myTasksTask1Button);
        Button task2Button = (Button) findViewById(R.id.myTasksTask2Button);
        Button task3Button = (Button) findViewById(R.id.myTasksTask3Button);

        taskButtonsArr[0]= task1Button;
        taskButtonsArr[1]= task2Button;
        taskButtonsArr[2]= task3Button;

        for (int i = 0; i < taskButtonsArr.length; i++) {
            int buttonIterator = i;
            taskButtonsArr[i].setOnClickListener(view ->
                    {
                        String taskName = ((Button) taskButtonsArr[buttonIterator]).getText().toString();

                        Intent goToTaskDetailIntent = new Intent(MyTasksActivity.this, TaskDetailActivity.class);
                        goToTaskDetailIntent.putExtra(TASK_NAME_TAG, taskName);
                        startActivity(goToTaskDetailIntent);
                    }
            );
        }

    }

}