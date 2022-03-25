package com.rmalexander.taskmaster.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rmalexander.taskmaster.R;
import com.rmalexander.taskmaster.activity.MyTasksActivity;
import com.rmalexander.taskmaster.activity.TaskDetailActivity;
import com.rmalexander.taskmaster.model.Task;

import org.w3c.dom.Text;

import java.util.List;

public class MyTasksRecyclerViewAdapter extends RecyclerView.Adapter<MyTasksRecyclerViewAdapter.TaskListViewHolder> {


    List<Task> taskList;
    Context callingActivity;
    private Long id;

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
        String taskTitle = taskList.get(position).getTitle();
        String taskStateString = taskList.get(position).getState().taskStateString;
        long taskId = taskList.get(position).getId();
        taskFragmentTextView.setText((position +1 ) + ". " + taskTitle + ": " + taskStateString);

        View taskViewHolder = holder.itemView;
        taskViewHolder.setOnClickListener(view -> {
            Intent goToTaskDetailIntent = new Intent(callingActivity, TaskDetailActivity.class);
            goToTaskDetailIntent.putExtra(MyTasksActivity.TASK_ID_EXTRA_TAG, taskId);
            //goToTaskDetailIntent.putExtra(MyTasksActivity.TASK_TITLE_EXTRA_TAG, taskTitle);
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
