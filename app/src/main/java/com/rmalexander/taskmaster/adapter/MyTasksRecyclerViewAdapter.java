package com.rmalexander.taskmaster.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.datastore.generated.model.Task;
import com.rmalexander.taskmaster.R;
import com.rmalexander.taskmaster.activity.MyTasksActivity;
import com.rmalexander.taskmaster.activity.TaskDetailActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class MyTasksRecyclerViewAdapter extends RecyclerView.Adapter<MyTasksRecyclerViewAdapter.TaskListViewHolder> {

    public static String TAG = "MyTasksRecyclerViewAdapter";

    List<Task> taskList;
    Context callingActivity;

    public MyTasksRecyclerViewAdapter(List<Task> taskList, Context callingActivity){

        this.taskList = taskList;
        this.callingActivity = callingActivity;
    }

    @NonNull
    @Override
    public TaskListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View taskFragment = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_task, parent, false);

       return new TaskListViewHolder(taskFragment);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskListViewHolder holder, int position) {
        TextView taskFragmentTextView = (TextView) holder.itemView.findViewById(R.id.taskFragmentTextView);
        Task displayedTask = taskList.get(position);
        DateFormat timeStampFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
        timeStampFormat.setTimeZone(TimeZone.getDefault());
        String timeStampString = "";

        try{
            {
                Date timeStampFormattedDate = timeStampFormat.parse(displayedTask.getDateAdded().format());
            }
        }
        catch (ParseException parseException) {
            Log.e(TAG, "Failed to reformat timestamp: " + parseException.getMessage(), parseException);
        }

        taskFragmentTextView.setText((position + 1 + ". " + displayedTask.getTitle()));

        View taskViewHolder = holder.itemView;
        taskViewHolder.setOnClickListener(view -> {
            Intent goToTaskDetailIntent = new Intent(callingActivity, TaskDetailActivity.class);
            goToTaskDetailIntent.putExtra(MyTasksActivity.TASK_ID_EXTRA_TAG, displayedTask.getId());
            callingActivity.startActivity(goToTaskDetailIntent);
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public static class TaskListViewHolder extends RecyclerView.ViewHolder {
        public TaskListViewHolder(View fragmentTaskView){
            super(fragmentTaskView);
        }
    }
}
