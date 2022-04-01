package com.rmalexander.taskmaster.activity;

import static com.rmalexander.taskmaster.activity.SettingsActivity.USERNAME_TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.TaskProgressEnum;
import com.amplifyframework.datastore.generated.model.Team;
import com.rmalexander.taskmaster.R;
import com.rmalexander.taskmaster.adapter.MyTasksRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyTasksActivity extends AppCompatActivity {

    SharedPreferences userPreferences;
    MyTasksRecyclerViewAdapter adapter;
    public final String TAG = "MyTasksActivity";
    public static final String TASK_TITLE_EXTRA_TAG = "taskTitle";
    public static final String TASK_ID_EXTRA_TAG = "taskId";
    List<Task> taskList = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tasks);

        //seedTeams();

        wireSettingsButton();
        wireAddTaskButton();
        wireAllTasksButton();

        String testDate = com.amazonaws.util.DateUtils.formatISO8601Date(new Date());

        List<Team> teamList = new ArrayList<>();

        /*Amplify.API.query(
                ModelQuery.list(Team.class),
                success ->
                {
                    Log.i(TAG, "Successfully loaded teamList");
                    teamList.clear();
                    for (Team databaseTeam : success.getData()){
                        teamList.add(databaseTeam);
                    };
                },
                failure -> Log.i(TAG, "Failed to load teamList")
        );*/

        /*Task testTask =
                Task.builder()
                    .title("Test app")
                    .description("functionality")
                    .dateAdded(new Temporal.DateTime(testDate))
                    .progress(TaskProgressEnum.New)
                        .teamName(Team teamOne)
                    .build();
        Amplify.API.mutate(
                ModelMutation.create(testTask),
                successResponse -> Log.i(TAG, "MyTasksActivity.onCreate(): successfully created new Task"),
                failureResponse -> Log.i(TAG, "MyTasksActivity.onCreate(): failed --" + failureResponse)
        );*/
                taskList = new ArrayList<>();

    }

    @Override
    protected void onResume() {
        super.onResume();

        userPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String username = userPreferences.getString(USERNAME_TAG, "");
        if (!username.equals("")) {
            TextView userTasksTitle = (TextView) findViewById(R.id.myTasksTitleTextView);
            String userTasksTitleText = username + "'s Tasks";
            userTasksTitle.setText(userTasksTitleText);
        }

        Amplify.API.query(
                ModelQuery.list(Task.class),
                success ->
                {
                    Log.i(TAG, "Successfully loaded taskList");
                    taskList.clear();
                    for (Task databaseTask : success.getData()){
                        taskList.add(databaseTask);
                    }

                    runOnUiThread(() -> {
                        adapter.notifyDataSetChanged();
                            });
                },
                failure -> Log.i(TAG, "Failed to load taskList")
        );

        wireMyTasksRecyclerView();
    }

    private void wireAddTaskButton() {
        Button addTaskButton = (Button) findViewById(R.id.myTasksAddTaskButton);

        addTaskButton.setOnClickListener(view ->
                {
                    Intent goToSettingsIntent = new Intent(MyTasksActivity.this, AddTaskActivity.class);
                    startActivity(goToSettingsIntent);
                }
        );
    }

    private void wireAllTasksButton() {
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


    private void wireMyTasksRecyclerView() {
        RecyclerView myTasksRecyclerView = (RecyclerView) findViewById(R.id.myTasksRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        myTasksRecyclerView.setLayoutManager(layoutManager);

        adapter = new MyTasksRecyclerViewAdapter(taskList, this);

        myTasksRecyclerView.setAdapter(adapter);
    }

    /*private void seedTeams(){
        Team teamOne =
                Team.builder()
                .teamName("Uno")
                .build();
        Amplify.API.mutate(
          ModelMutation.create(teamOne),
                successResponse -> Log.i(TAG, "MyTasksActivity.seedTeams(): Successfully created Team 1"),
                failureResponse -> Log.i(TAG, "MyTasksActivity.seedTeams(): Failed to create Team 1")
        );

        Team teamTwo =
                Team.builder()
                        .teamName("Dos")
                        .build();
        Amplify.API.mutate(
                ModelMutation.create(teamTwo),
                successResponse -> Log.i(TAG, "MyTasksActivity.seedTeams(): Successfully created Team 2"),
                failureResponse -> Log.i(TAG, "MyTasksActivity.seedTeams(): Failed to create Team 2")
        );

        Team teamThree =
                Team.builder()
                        .teamName("Tres")
                        .build();
        Amplify.API.mutate(
                ModelMutation.create(teamThree),
                successResponse -> Log.i(TAG, "MyTasksActivity.seedTeams(): Successfully created Team 3"),
                failureResponse -> Log.i(TAG, "MyTasksActivity.seedTeams(): Failed to create Team 3")
        );

    }*/

}