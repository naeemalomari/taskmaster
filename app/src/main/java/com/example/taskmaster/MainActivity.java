package com.example.taskmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

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
import android.widget.Toast;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;

import java.util.ArrayList;
import java.util.List;

import com.amplifyframework.datastore.generated.model.Tasks;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "tag";

    TaskDao taskDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            Amplify.addPlugin(new AWSDataStorePlugin()); // stores records locally
            Amplify.addPlugin(new AWSApiPlugin()); // stores things in DynamoDB and allows us to perform GraphQL queries
            Amplify.configure(getApplicationContext());

            Log.i(TAG, "Initialized Amplify");
        } catch (AmplifyException error) {
            Log.e(TAG, "Could not initialize Amplify", error);
        }

//        List<Tasks> tasksData = TasksDatabase.getInstance(this).taskDao().getAll();
        List<TasksOrg> tasksData = new ArrayList<>();

        RecyclerView allTaskDataRecyclerView = findViewById(R.id.recylerViewId);

        allTaskDataRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        allTaskDataRecyclerView.setAdapter(new TaskAdapter(tasksData, new TaskAdapter.OnTaskItemClickListener() {
            @Override
            public void onItemClicked(int position) {
                Intent intentTaskDetails = new Intent(getApplicationContext(), TaskDetailPage.class);
                intentTaskDetails.putExtra("title", tasksData.get(position).title);
                intentTaskDetails.putExtra("body", tasksData.get(position).body);
                intentTaskDetails.putExtra("state", tasksData.get(position).state);
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

        Amplify.API.query(
                ModelQuery.list(com.amplifyframework.datastore.generated.model.Tasks.class),
                response -> {
                    for (Tasks todo : response.getData()) {
                        TasksOrg tasksOrg = new TasksOrg(todo.getTitle(), todo.getBody(), todo.getState());
                        Log.i("graph testing", todo.getTitle());
                        tasksData.add(tasksOrg);
                    }
                    handler.sendEmptyMessage(1);
                },
                error -> Log.e("MyAmplifyApp", "Query failure", error)
        );

        Button addTaskButton = findViewById(R.id.addTask);
        addTaskButton.setOnClickListener((view -> {
            Intent intent = new Intent(MainActivity.this, AddTask.class);
            startActivity(intent);
        }));


        Log.i(TAG, "onCreate: movingToAddTasks");
        Button button1 = findViewById(R.id.button);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainActivity.this, MainActivity2.class);
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


//        Log.i(TAG, "onCreate:movingToAllTasks ");
//        Button button5 =findViewById(R.id.button5);
//        button5.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view ){
//                Intent intent2 = new Intent(MainActivity.this,TaskDetailPage.class);
//                String task1 = button5.getText().toString();
//                intent2.putExtra("title", task1);
//                startActivity(intent2);
//            }
//        });
//
//        Log.i(TAG, "onCreate:movingToAllTasks ");
//        Button button6 =findViewById(R.id.button6);
//        button6.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view ){
//                Intent intent2 = new Intent(MainActivity.this,TaskDetailPage.class);
//                String task1 = button6.getText().toString();
//                intent2.putExtra("title", task1);
//                startActivity(intent2);
//            }
//        });
//        Log.i(TAG, "onCreate:movingToAllTasks ");
//
//
//
//        Button button7 =findViewById(R.id.button7);
//        button7.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view ){
//                Intent intent2 = new Intent(MainActivity.this,TaskDetailPage.class);
//                String task1 = button7.getText().toString();
//                intent2.putExtra("title", task1);
//                startActivity(intent2);
//            }
//
//        });

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

    //    public void getTask1(View view) {
//        Intent taskDetail = new Intent(this,TaskDetailPage.class);
//        taskDetail.putExtra("title", "Task1");
//        startActivity(taskDetail);
//    }
//
//    public void getTask2(View view) {
//        Intent taskDetail = new Intent(this,TaskDetailPage.class);
//        taskDetail.putExtra("title", "Task2");
//        startActivity(taskDetail);
//    }
//
//    public void getTask3(View view) {
//        Intent taskDetail = new Intent(this,TaskDetailPage.class);
//        taskDetail.putExtra("title", "Task3");
//        startActivity(taskDetail);
//    }


    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String saveButton1 = sharedPreferences.getString("username", "here is your name : ");

        TextView username = findViewById(R.id.textView7);
        username.setText("UserName " + saveButton1);


    }
}