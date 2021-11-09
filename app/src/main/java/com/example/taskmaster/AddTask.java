package com.example.taskmaster;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.Tasks;
import com.amplifyframework.datastore.generated.model.Team;

import java.util.ArrayList;
import java.util.List;

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


                RadioButton RadioButtonFirstTeam = findViewById(R.id.radioButton1);
                RadioButton RadioButtonSecondTeam = findViewById(R.id.radioButton2);
                RadioButton RadioButtonThirdTeam = findViewById(R.id.radioButton3);

                List<Team> allTeam = new ArrayList<>();

                Amplify.API.query(
                        ModelQuery.list(Team.class),
                        response -> {
                            for (Team team : response.getData()) {
                                Log.i("MyAmplifyApp", team.getName());

                                allTeam.add(team);


                                System.out.println("here is the data all team " + allTeam);
                            }
                            Log.i("MyAmplifyApp", "outside the loop");
                        },
                        error -> Log.e("MyAmplifyApp", "Query failure", error)
                );


                String teamName = "";
                if (RadioButtonFirstTeam.isChecked()) {
//                            teamName = "First team";
                    teamName = RadioButtonFirstTeam.getText().toString();

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
