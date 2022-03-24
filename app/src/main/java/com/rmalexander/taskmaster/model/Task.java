package com.rmalexander.taskmaster.model;

public class Task {

    public enum TaskState {
        New("New"),
        Assigned("Assigned"),
        InProgress("In Progress"),
        Complete("Complete");

        public final String taskStateString;

        private TaskState(String taskStateString) {
            this.taskStateString = taskStateString;
        }

    }

String title;
String body;
TaskState state;

    public Task(String title, String body, TaskState state){
        this.title = title;
        this.body = body;
        this.state = state;
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

    public TaskState getState() {
        return state;
    }

    public void setState(TaskState state) {
        this.state = state;
    }
}
