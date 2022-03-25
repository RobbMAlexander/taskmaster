package com.rmalexander.taskmaster.model;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Task {

    public enum TaskStateEnum {
        New("New"),
        Assigned("Assigned"),
        InProgress("In Progress"),
        Complete("Complete");

        public final String taskStateString;

        private TaskStateEnum(String taskStateString) {
            this.taskStateString = taskStateString;
        }

    }

    @PrimaryKey(autoGenerate = true)
    public Long id;

    String title;
    String body;
    TaskStateEnum state;
    public Date dateAdded;

    public Task(String title, String body, TaskStateEnum state) {
        this.title = title;
        this.body = body;
        this.state = state;
        this.dateAdded = new Date();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public TaskStateEnum getState() {
        return state;
    }

    public void setState(TaskStateEnum state) {
        this.state = state;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public Long getId() {
        return id;
    }

}
