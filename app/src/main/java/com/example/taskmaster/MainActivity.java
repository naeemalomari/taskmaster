package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "tag";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Tasks> tasksData=new ArrayList<Tasks>();
        tasksData.add(new Tasks("LinkedList", "Don't lose your reference", "in progress"));
        tasksData.add(new Tasks("Stack", " Don't lose your reference ", "assigned"));
        tasksData.add(new Tasks("Queue", " Don't lose your reference ", "complete"));
        tasksData.add(new Tasks("Tree", " specify the tree root ", "new"));
        tasksData.add(new Tasks("Mock", " be prepared for any attack", "HARD"));

        RecyclerView allTaskDataRecyclerView= findViewById(R.id.recylerViewId);

        allTaskDataRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        allTaskDataRecyclerView.setAdapter(new TaskAdapter(tasksData));


        Log.i(TAG, "onCreate: movingToAddTasks");
        Button button1 = findViewById(R.id.button);
        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view){
                Intent intent1= new Intent(MainActivity.this,MainActivity2.class );
            startActivity(intent1);
            }
        });

        Log.i(TAG, "onCreate:movingToAllTasks ");
        Button button2 =findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view ){
                Intent intent2 = new Intent(MainActivity.this,MainActivity3.class);

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
        Button saving =findViewById(R.id.usernameBtn);
        saving.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view ){
                Intent intent2 = new Intent(MainActivity.this,SettingPage.class);
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