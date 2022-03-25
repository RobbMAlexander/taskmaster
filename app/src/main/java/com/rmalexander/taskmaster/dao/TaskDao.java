package com.rmalexander.taskmaster.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.rmalexander.taskmaster.model.Task;

import java.util.List;

@Dao
public interface TaskDao {
    @Insert
    public void insertTask(Task insertedTask);

    @Query("SELECT * FROM Task")
    public List<Task> findAll();

    @Query("SELECT * FROM Task ORDER BY title DESC")
    public List<Task> findAllSortedByTitle();

    @Query("SELECT * FROM Task ORDER BY state DESC")
    public List<Task> findAllSortedByState();

    @Query("SELECT * FROM Task WHERE id = :id")
    Task findById (long id);
}
