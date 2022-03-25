package com.rmalexander.taskmaster.activity;

import static com.rmalexander.taskmaster.activity.SettingsActivity.USERNAME_TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.rmalexander.taskmaster.R;
import com.rmalexander.taskmaster.adapter.MyTasksRecyclerViewAdapter;
import com.rmalexander.taskmaster.database.TaskMasterDatabase;
import com.rmalexander.taskmaster.model.Task;

import java.util.ArrayList;
import java.util.List;

public class MyTasksActivity extends AppCompatActivity {

    SharedPreferences userPreferences;
    MyTasksRecyclerViewAdapter adapter;
    public static final String TASK_TITLE_EXTRA_TAG = "taskTitle";
    public static final String TASK_ID_EXTRA_TAG = "taskId";
    List<Task> taskList = null;
    TaskMasterDatabase taskMasterDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tasks);

        taskMasterDatabase = Room.databaseBuilder(
                getApplicationContext(),
                TaskMasterDatabase.class,
                "rmalexander_taskmaster")
                .allowMainThreadQueries() //NOT FOR REAL-WORLD APPLICATIONS!
                .build();
        taskList = taskMasterDatabase.taskDao().findAll();

        wireSettingsButton();
        //wireTaskButtons();
        wireAddTaskButton();
        wireAllTasksButton();
        wireMyTasksRecyclerView();


    }

    @Override
    protected void onResume() {
        super.onResume();
        //TODO: refactor for preference load on onCreate?
        userPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String username = userPreferences.getString(USERNAME_TAG, "");
        if (!username.equals("")) {
            TextView userTasksTitle = (TextView) findViewById(R.id.myTasksTitleTextView);
            String userTasksTitleText = username + "'s Tasks";
            userTasksTitle.setText(userTasksTitleText);
        }

        taskList = taskMasterDatabase.taskDao().findAll();
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

    // TODO: Remove deprecated hard-coded task buttons when done attempting programmatic generation
    /*private void wireTaskButtons() {
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
                        goToTaskDetailIntent.putExtra(TASK_TITLE_EXTRA_TAG, taskName);
                        startActivity(goToTaskDetailIntent);
                    }
            );
        }

    }*/

    private void wireMyTasksRecyclerView(){
        RecyclerView myTasksRecyclerView = (RecyclerView) findViewById(R.id.myTasksRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        myTasksRecyclerView.setLayoutManager(layoutManager);

        adapter = new MyTasksRecyclerViewAdapter(taskList, this);

        myTasksRecyclerView.setAdapter(adapter);
    }

}