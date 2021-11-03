package com.example.taskmaster;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM tasks")
    List<Tasks> getAll();

    @Query("SELECT * FROM tasks WHERE id=:id")
    Tasks getTasksById(Long id);
    @Insert
    Long insertTasks(Tasks tasks);

     @Update
     Long updateTask(Tasks tasks);

    @Delete
    Long deleteTasks(Tasks tasks);

}
