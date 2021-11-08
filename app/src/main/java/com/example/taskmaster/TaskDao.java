package com.example.taskmaster;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM TasksOrg")
     List<TasksOrg> getAll() ;

    @Query("SELECT * FROM TasksOrg WHERE id=:id")
    TasksOrg getTasksById(Long id);
    @Insert
    Long insertTasks(TasksOrg tasksOrg);

     @Update
     void updateTasks(TasksOrg tasksOrg);

    @Delete
    void deleteTasks(TasksOrg tasksOrg);

}
