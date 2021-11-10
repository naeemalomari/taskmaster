package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.Team;

import java.util.ArrayList;
import java.util.List;

public class AddTask extends AppCompatActivity {

    private RadioButton radioButtonFirstTeam;
    private RadioButton radioButtonFirstTeam1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

//save to db
        EditText taskTitle = findViewById(R.id.titleInput);
        EditText taskBody = findViewById(R.id.bodyInput);
        EditText taskState = findViewById(R.id.stateInput);
        Button button = findViewById(R.id.submitTaskInfo);

        List<Team> allTeam = new ArrayList<>();

        RadioButton RadioButtonFirstTeam = findViewById(R.id.radioButton1);
        RadioButton RadioButtonSecondTeam = findViewById(R.id.radioButton2);
        RadioButton RadioButtonThirdTeam = findViewById(R.id.radioButton3);

        /// lab33 1. to get  team name data from data base



        Amplify.API.query(
                ModelQuery.list(Team.class),
                response -> {
                    for (Team team : response.getData()) {
                        Log.i("MyAmplifyApp", team.getName());
                        allTeam.add(team);

                        System.out.println("here is the data all team " + allTeam);
                        RadioButtonFirstTeam.setText(team.getName());
                        RadioButtonSecondTeam.setText(team.getName());
                        RadioButtonThirdTeam.setText(team.getName());

                    }
                    Log.i("MyAmplifyApp", "outside the loop");
                },
                error -> Log.e("MyAmplifyApp", "Query failure", error)
        );


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = taskTitle.getText().toString();
                String body = taskBody.getText().toString();
                String state = taskState.getText().toString();
////
//                    taskDatabase = Room.databaseBuilder(getApplicationContext(), TaskDatabase.class, "tasks").allowMainThreadQueries().fallbackToDestructiveMigration().build();
//
//                    Task task = new Task(title,body,state);
//                    taskDatabase.taskDao().insertAll(task);


                String teamName = "";
                if (RadioButtonFirstTeam.isChecked()) {
//                            teamName = "First team";
                    teamName = radioButtonFirstTeam.getText().toString();

                } else if (RadioButtonSecondTeam.isChecked()) {
//                            teamName = "Tow team";
                    teamName = RadioButtonSecondTeam.getText().toString();

//
                } else if (RadioButtonThirdTeam.isChecked()) {
//                            teamName = "Three team";
                    teamName = RadioButtonThirdTeam.getText().toString();

//
                }
                Team selectedTeam = null;
                for (Team teams : allTeam) {
                    if (teams.getName().equals(teamName)) {
                        selectedTeam = teams;
                    }

                }

                Task todo = Task.builder()
                        .title(title)
                        .teams(selectedTeam)
                        .body(body).state(state).build();

                Amplify.API.mutate(
                        ModelMutation.create(todo),
                        response2 -> Log.i("MyAmplifyApp", "Added Todo with id: " + response2.getData().getId()),
                        error -> Log.e("MyAmplifyApp", "Create failed", error)
                );


                Intent goToHomePage = new Intent(AddTask.this, MainActivity.class);
                startActivity(goToHomePage);
            }
        });
    }
}
