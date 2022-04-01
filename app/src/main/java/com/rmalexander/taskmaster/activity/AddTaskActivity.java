package com.rmalexander.taskmaster.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.TaskProgressEnum;
import com.amplifyframework.datastore.generated.model.Team;
import com.google.android.material.snackbar.Snackbar;
import com.rmalexander.taskmaster.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class AddTaskActivity extends AppCompatActivity {

    public static final String TAG = "AddTaskActivity";

    Spinner teamSelectSpinner;
    Spinner taskProgressSpinner;

    Spinner a = null;
    Spinner b = null;

    List<Team> teamList;
    CompletableFuture<List<Team>> teamListFuture = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        //TODO: bring in Team objects
        teamListFuture = new CompletableFuture<>();

       wireAddTaskSpinners();
      wireAddTaskButton();

    }

    private void wireAddTaskSpinners() {

        teamSelectSpinner = ((Spinner) findViewById(R.id.addTaskTeamSpinner));
        taskProgressSpinner = ((Spinner) findViewById(R.id.addTaskProgressSpinner));

        Amplify.API.query(
                ModelQuery.list(Team.class),
                success -> {
                    Log.i(TAG, "Successfully loaded Team list.");
                    ArrayList<Team> teamList = new ArrayList<>();
                    ArrayList<String> teamNameList = new ArrayList<>();
                    for (Team displayedTeam : success.getData()) {
                        teamList.add(displayedTeam);
                        teamNameList.add(displayedTeam.getTeamName());
                    }
                    teamListFuture.complete(teamList);

                    runOnUiThread(() -> {
                        teamSelectSpinner.setAdapter(new ArrayAdapter<>(
                                this,
                                android.R.layout.simple_spinner_item,
                                teamNameList));
                    });
                },
                failure -> {
                    teamListFuture.complete(null);
                    Log.i(TAG, "Failed to load Team list");
                }

        );

        taskProgressSpinner.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                TaskProgressEnum.values()));
    }

    private void wireAddTaskButton() {

        Button addTaskButton = (Button) findViewById(R.id.addTaskAddTaskButton);

        addTaskButton.setOnClickListener(view -> {
            {
                String title = ((EditText) findViewById(R.id.addTaskTaskTitleTextEdit)).getText().toString();
                String description = ((EditText) findViewById(R.id.addTaskTaskDescpritionTextEdit)).getText().toString();
                String timeStampString = com.amazonaws.util.DateUtils.formatISO8601Date(new Date());
                String selectedTeamString = teamSelectSpinner.getSelectedItem().toString();

                List<Team> teamList = null;
                try {
                    teamList = teamListFuture.get();
                } catch (InterruptedException interruptedException){
                    Log.e(TAG, "InterruptedException occurred during: teamListFuture.get()");
                    Thread.currentThread().interrupt();
            } catch (ExecutionException executionException) {
                    Log.e(TAG, "ExecutionException occurred during: teamListFuture.get()");
                }

                Team selectedTeam = teamList.stream().filter(team -> team.getTeamName().equals(selectedTeamString)).findAny().orElseThrow(RuntimeException::new);

                Task addedTask = Task.builder()
                        .title(title)
                        .description(description)
                        .dateAdded(new Temporal.DateTime(timeStampString))
                        .progress((TaskProgressEnum) taskProgressSpinner.getSelectedItem())
                        .teamName(selectedTeam)
                        .build();

                Amplify.API.mutate(
                        ModelMutation.create(addedTask),
                        successResponse -> Log.i(TAG, "AddTaskActivity.onCreate(): successfully added a new task"),
                        failureResponse -> Log.i(TAG, "AddTaskActivity.onCreate(): failed to add new task -- " + failureResponse)
                );

                Snackbar.make(findViewById(R.id.addTaskAddTaskButton), "Task Added", Snackbar.LENGTH_SHORT).show();
            }
        });

    }
}