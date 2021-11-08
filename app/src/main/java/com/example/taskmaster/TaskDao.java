package com.example.taskmaster;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM TasksOrginal")
     List<TasksOrginal> getAll() ;

    @Query("SELECT * FROM TasksOrginal WHERE id=:id")
    TasksOrginal getTasksById(Long id);
    @Insert
    Long insertTasks(TasksOrginal tasksOrginal);

     @Update
     void updateTasks(TasksOrginal tasksOrginal);

    @Delete
    void deleteTasks(TasksOrginal tasksOrginal);

}
