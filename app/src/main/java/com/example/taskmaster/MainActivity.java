package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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


    }
}