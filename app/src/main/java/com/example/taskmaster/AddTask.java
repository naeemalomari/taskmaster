package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Tasks;

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

//                Tasks tasks = new Tasks(title, body, state);
//                Long addedTaskID = TasksDatabase.getInstance(getApplicationContext()).taskDao().insertTasks(tasks);

//                System.out.println("*********************" + addedTaskID + "**********************************************");
                Button addTaskButton = findViewById(R.id.addTask);

//                addTaskButton.setOnClickListener((view1 -> {


                    Tasks tasksOrg = Tasks.builder()
                            .title(title)
                            .body(body)
                            .state(state)
                            .build();

                    Amplify.API.mutate(
                            ModelMutation.create(tasksOrg),
                            response -> Log.i("MyAmplifyApp", "Added Todo with id: " + response.getData().getId()),
                            error -> Log.e("MyAmplifyApp", "Create failed", error)
                    );

                    Intent intent = new Intent(AddTask.this, MainActivity.class);
                    startActivity(intent);


//                }));
            }
        });

    }
}