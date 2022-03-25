package com.rmalexander.taskmaster.database;


import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.rmalexander.taskmaster.dao.TaskDao;
import com.rmalexander.taskmaster.model.Task;

@Database(entities = {Task.class}, version = 1)
@TypeConverters({TaskMasterDatabaseConverters.class})
public abstract class TaskMasterDatabase extends RoomDatabase {
    public abstract TaskDao taskDao();
}
