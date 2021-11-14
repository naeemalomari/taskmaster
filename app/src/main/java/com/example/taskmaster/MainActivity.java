package com.example.taskmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;

import java.util.ArrayList;
import java.util.List;

import com.amplifyframework.datastore.generated.model.Task;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "tag";

    TaskDao taskDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            /////////to add three hard coding to team /////////////////

            // Add these lines to add the AWSApiPlugin plugins
            Amplify.addPlugin(new AWSDataStorePlugin());
            Amplify.addPlugin(new AWSApiPlugin()); // stores things in DynamoDB and allows us to perform GraphQL queries
            Amplify.configure(getApplicationContext());
//
//            Log.i("MyAmplifyApp", "Initialized Amplify");
//            Team team = Team.builder()
//                    .name("First team")
//                    .build();
//
//            Amplify.API.mutate(
//                    ModelMutation.create(team),
//                    response -> Log.i("MyAmplifyApp", "Added Todo with id: " + response.getData().getId()),
//                    error -> Log.e("MyAmplifyApp", "Create failed", error)
//            );
//
//            ///second team
//
//            Team teamTow = Team.builder()
//                    .name("Two team")
//                    .build();
//
//            Amplify.API.mutate(
//                    ModelMutation.create(teamTow),
//                    response -> Log.i("MyAmplifyApp", "Added Todo with id: " + response.getData().getId()),
//                    error -> Log.e("MyAmplifyApp", "Create failed", error)
//            );
//
//            ////third team hard coby
//
//            Team teamThree = Team.builder()
//                    .name("Three team")
//                    .build();
//
//            Amplify.API.mutate(
//                    ModelMutation.create(teamThree),
//                    response -> Log.i("MyAmplifyApp", "Added Todo with id: " + response.getData().getId()),
//                    error -> Log.e("MyAmplifyApp", "Create failed", error)
//            );
        } catch (AmplifyException error) {
            Log.e("MyAmplifyApp", "Could not initialize Amplify", error);
        }
        Button signOut=findViewById(R.id.logout);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Amplify.Auth.signOut(
                        () -> Log.i("AuthQuickstart", "Signed out successfully"),
                        error -> Log.e("AuthQuickstart", error.toString())
                );
                Intent intent = new Intent(MainActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

//        List<Tasks> tasksData = TasksDatabase.getInstance(this).taskDao().getAll();
        List<Task> tasksData = new ArrayList<>();
        ArrayList<Task> teams = new ArrayList<>();

        RecyclerView allTaskDataRecyclerView = findViewById(R.id.recylerViewId);

        allTaskDataRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        allTaskDataRecyclerView.setAdapter(new TaskAdapter(teams, new TaskAdapter.OnTaskItemClickListener() {
            @Override
            public void onItemClicked(int position) {
                Intent intentTaskDetails = new Intent(getApplicationContext(), TaskDetailPage.class);
                intentTaskDetails.putExtra("title", teams.get(position).getTitle());
                intentTaskDetails.putExtra("body", teams.get(position).getBody());
                intentTaskDetails.putExtra("state", teams.get(position).getState());
                startActivity(intentTaskDetails);

            }
        }));


        Handler handler = new Handler(Looper.myLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                allTaskDataRecyclerView.getAdapter().notifyDataSetChanged();
                return false;
            }
        });

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        String teamNameString = sharedPreferences.getString("teamName", "team name");
        TextView teamNameView = findViewById(R.id.teamNameId);
        teamNameView.setText(teamNameString);

        Amplify.API.query(
                ModelQuery.list(Task.class),
                response -> {
                    ///looping through data to render it
                    for (Task taskMaster2 : response.getData()) {
                        ///add new data to array
                        tasksData.add(taskMaster2);
                        for (int i = 0; i < tasksData.size(); i++) {
                            if (tasksData.get(i).getTeams().getName().equals(teamNameString)) {
                                teams.add(tasksData.get(i));
                            }
                        }
                    }
                    //handel promise and wait to get all data
                    handler.sendEmptyMessage(1);
                    Log.i("MyAmplifyApp", "outsoid the loop");
                },
                error -> Log.e("MyAmplifyApp", "Query failure", error)
        );
        Button addTaskButton = findViewById(R.id.addTask);
        addTaskButton.setOnClickListener((view -> {
            Intent intent = new Intent(MainActivity.this, AddTask.class);
            startActivity(intent);
        }));
        Log.i(TAG, "onCreate: movingToAddTasks");
        Button button1 = findViewById(R.id.logout);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent1);
            }
        });

        Log.i(TAG, "onCreate:movingToAllTasks ");
        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MainActivity.this, MainActivity3.class);

                startActivity(intent2);
            }
        });


        Log.i(TAG, "onCreate:movingToAllTasks ");
        Button saving = findViewById(R.id.usernameBtn);
        saving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MainActivity.this, SettingPage.class);
                startActivity(intent2);
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String saveButton1 = sharedPreferences.getString("username", "here is your name : ");

        TextView username = findViewById(R.id.textView7);
        username.setText(saveButton1);
    }
}