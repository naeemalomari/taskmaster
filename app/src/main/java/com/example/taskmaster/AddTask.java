package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class AddTask extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        Button addTasksBTN = findViewById(R.id.submitTaskInfo);
        addTasksBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText titleInputField = findViewById(R.id.titleInput);
                String title = titleInputField.getText().toString();

                EditText bodyInputField = findViewById(R.id.bodyInput);
                String body = bodyInputField.getText().toString();

                EditText StateInputField = findViewById(R.id.stateInput);
                String state = StateInputField.getText().toString();

                Tasks tasks = new Tasks(title, body, state);
                Long addedTaskID = TasksDatabase.getInstance(getApplicationContext()).taskDao().insertTasks(tasks);

                System.out.println("*********************" + addedTaskID + "**********************************************");
                Button addTaskButton=findViewById(R.id.addTask);

//                addTaskButton.setOnClickListener((view1 -> {
//                    Intent intent = new Intent(AddTask.this, MainActivity.class);
//                    startActivity(intent);
//
//
//                }));
            }
        });

    }
}