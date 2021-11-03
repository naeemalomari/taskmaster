package com.example.taskmaster;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Tasks.class}, version = 1)
public abstract class TasksDatabase extends RoomDatabase {
    public abstract TaskDao taskDao();
    private static TasksDatabase tasksDatabase;


    public TasksDatabase() {
    }

    public static synchronized TasksDatabase getInstance(Context context) {
        if (tasksDatabase == null) {
            tasksDatabase = Room.databaseBuilder(context, TasksDatabase.class,
                    "taskDatabase").allowMainThreadQueries().build();
        }
        return tasksDatabase;
    }

}