package com.example.taskmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
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

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.pinpoint.targeting.TargetingClient;
import com.amazonaws.mobileconnectors.pinpoint.targeting.endpointProfile.EndpointProfile;
import com.amazonaws.mobileconnectors.pinpoint.targeting.endpointProfile.EndpointProfileUser;
import com.amplifyframework.AmplifyException;
import com.amplifyframework.analytics.AnalyticsEvent;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;

import java.util.ArrayList;
import java.util.List;

import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.Team;
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.amazonaws.mobileconnectors.pinpoint.PinpointConfiguration;
import com.amazonaws.mobileconnectors.pinpoint.PinpointManager;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "tag";

    TaskDao taskDao;
    private static PinpointManager pinpointManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button signOut = findViewById(R.id.logout);

        configure();

        //38
        getPinpointManager(getApplicationContext());
        assignUserIdToEndpoint();
        recordEvents();

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Amplify.Auth.signOut(
                        () -> Log.i("AuthQuickstart", "Signed out successfully"),
                        error -> Log.e("AuthQuickstart", error.toString())
                );
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
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
    protected void onStart() {
        super.onStart();

//        List<Tasks> tasksData = TasksDatabase.getInstance(this).taskDao().getAll();

        List<Task> tasksData = new ArrayList<>();
        List<Task> team = new ArrayList<>();

        RecyclerView allTaskDataRecyclerView = findViewById(R.id.recylerViewId);

        allTaskDataRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        allTaskDataRecyclerView.setAdapter(new TaskAdapter(team, new TaskAdapter.OnTaskItemClickListener() {
            @Override
            public void onItemClicked(int position) {
                Intent intentTaskDetails = new Intent(getApplicationContext(), TaskDetailPage.class);
                intentTaskDetails.putExtra("title", team.get(position).getTitle());
                intentTaskDetails.putExtra("body", team.get(position).getBody());
                intentTaskDetails.putExtra("state", team.get(position).getState());
                intentTaskDetails.putExtra("img", team.get(position).getImg());
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
                            Log.i("IAM ADDING ", "HHHHIIIIIIIIIIIII");
                            if (tasksData.get(i).getTeams().getName().equals(teamNameString)) {
                                team.add(tasksData.get(i));

                            }
                        }
                    }
                    //handle promise and wait to get all data

                    Log.i("MyAmplifyApp", "outside the loop");
                    handler.sendEmptyMessage(1);
                },
                error -> Log.e("MyAmplifyApp", "Query failure", error)
        );

    }
//38
    public static PinpointManager getPinpointManager(final Context applicationContext) {
        if (pinpointManager == null) {
            final AWSConfiguration awsConfig = new AWSConfiguration(applicationContext);
            AWSMobileClient.getInstance().initialize(applicationContext, awsConfig, new Callback<UserStateDetails>() {
                @Override
                public void onResult(UserStateDetails userStateDetails) {
                    Log.i("INIT", userStateDetails.getUserState().toString());
                }

                @Override
                public void onError(Exception e) {
                    Log.e("INIT", "Initialization error.", e);
                }
            });

            PinpointConfiguration pinpointConfig = new PinpointConfiguration(
                    applicationContext,
                    AWSMobileClient.getInstance(),
                    awsConfig);

            pinpointManager = new PinpointManager(pinpointConfig);

            FirebaseMessaging.getInstance().getToken()
                    .addOnCompleteListener(new OnCompleteListener<String>() {
                        @Override
                        public void onComplete(@NonNull com.google.android.gms.tasks.Task<String> task) {
                            if (!task.isSuccessful()) {
                                Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                                return;
                            }
                            final String token = task.getResult();
                            Log.d(TAG, "Registering push notifications token: " + token);
                            pinpointManager.getNotificationClient().registerDeviceToken(token);
                        }
                    });
        }
        return pinpointManager;
    }
//38
    public void assignUserIdToEndpoint() {
        TargetingClient targetingClient = pinpointManager.getTargetingClient();
        EndpointProfile endpointProfile = targetingClient.currentEndpoint();
        EndpointProfileUser endpointProfileUser = new EndpointProfileUser();
        endpointProfileUser.setUserId("UserIdValue");
        endpointProfile.setUser(endpointProfileUser);
        targetingClient.updateEndpointProfile(endpointProfile);
        Log.d(TAG, "Assigned user ID " + endpointProfileUser.getUserId() +
                " to endpoint " + endpointProfile.getEndpointId());
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String saveButton1 = sharedPreferences.getString("userNameAPI", "");

        TextView username = findViewById(R.id.textView7);
        username.setText(saveButton1);
    }


    private void configure() {

        try {
            /////////to add three hard coding to team /////////////////

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
            // Add these lines to add the AWSApiPlugin plugins
            Amplify.addPlugin(new AWSDataStorePlugin());
            Amplify.addPlugin(new AWSApiPlugin()); // stores things in DynamoDB and allows us to perform GraphQL queries
            Amplify.configure(getApplicationContext());
        } catch (AmplifyException error) {
            Log.e("MyAmplifyApp", "Could not initialize Amplify", error);
        }
    }
    public void recordEvents(){
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