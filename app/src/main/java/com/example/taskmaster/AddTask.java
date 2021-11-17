package com.example.taskmaster;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.analytics.AnalyticsEvent;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.Team;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class AddTask extends AppCompatActivity {

    String img = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        recordEvents();

//save to db
        EditText taskTitle = findViewById(R.id.titleInput);
        EditText taskBody = findViewById(R.id.bodyInput);
        EditText taskState = findViewById(R.id.stateInput);

        Button button2111111 = findViewById(R.id.submitTaskInfo);


        RadioButton RadioButtonFirstTeam = findViewById(R.id.radioButton1);
        RadioButton RadioButtonSecondTeam = findViewById(R.id.radioButton2);
        RadioButton RadioButtonThirdTeam = findViewById(R.id.radioButton3);

        Button addFile = findViewById(R.id.uploadImg);
        addFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFileFromDevice();
            }
        });

        List<Team> allTeam = new ArrayList<>();

        Amplify.API.query(
                ModelQuery.list(Team.class),
                response -> {
                    for (Team team : response.getData()) {
                        Log.i("MyAmplifyApp", team.getName());
                        allTeam.add(team);

                        System.out.println("here is the data all team " + allTeam);
//                        RadioButtonFirstTeam.setText(team.getName());
//                        RadioButtonSecondTeam.setText(team.getName());
//                        RadioButtonThirdTeam.setText(team.getName());

                    }
                    Log.i("MyAmplifyApp", "outside the loop");
                },
                error -> Log.e("MyAmplifyApp", "Query failure", error)
        );


        button2111111.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = taskTitle.getText().toString();
                String body = taskBody.getText().toString();
                String state = taskState.getText().toString();

//                    taskDatabase = Room.databaseBuilder(getApplicationContext(), TaskDatabase.class, "tasks").allowMainThreadQueries().fallbackToDestructiveMigration().build();
//
//                    Task task = new Task(title,body,state);
//                    taskDatabase.taskDao().insertAll(task);


                String teamName = "";
                if (RadioButtonFirstTeam.isChecked()) {
//                            teamName = "First team";
                    teamName = RadioButtonFirstTeam.getText().toString();

                } else if (RadioButtonSecondTeam.isChecked()) {
//                            teamName = "Two team";
                    teamName = RadioButtonSecondTeam.getText().toString();


                } else if (RadioButtonThirdTeam.isChecked()) {
//                            teamName = "Three team";
                    teamName = RadioButtonThirdTeam.getText().toString();

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
                        .body(body).img(img).state(state).build();

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

    private void getFileFromDevice() {
        Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
        chooseFile.setType("*/*");
        chooseFile = Intent.createChooser(chooseFile, "Choose a File");
        startActivityForResult(chooseFile, 1234);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        File uploadFile = new File(getApplicationContext().getFilesDir(), "uploadFileCopied");
        try {
            InputStream exampleInputStream = getContentResolver().openInputStream(data.getData());
            OutputStream outputStream = new FileOutputStream(uploadFile);
            img = data.getData().toString();
            byte[] buff = new byte[1024];
            int length;
            while ((length = exampleInputStream.read(buff)) > 0) {
                outputStream.write(buff, 0, length);
            }
            exampleInputStream.close();
            outputStream.close();
            Amplify.Storage.uploadFile(
                    "image",
                    uploadFile,
                    result -> Log.i("MyAmplifyApp", "Successfully uploaded: " + result.getKey()),
                    storageFailure -> Log.e("MyAmplifyApp", "Upload failed", storageFailure)
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void recordEvents() {
        AnalyticsEvent event = AnalyticsEvent.builder()
                .name("PasswordReset")
                .addProperty("Channel", "SMS")
                .addProperty("Successful", true)
                .addProperty("ProcessDuration", 792)
                .addProperty("UserAge", 120.3)
                .build();

        Amplify.Analytics.recordEvent(event);
    }
}
