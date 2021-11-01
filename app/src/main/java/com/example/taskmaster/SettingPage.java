package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SettingPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_page);


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        findViewById(R.id.saveButton).setOnClickListener(view -> {


            Toast clickToast = Toast.makeText(getApplicationContext(), "SAVED!!", Toast.LENGTH_LONG);
            clickToast.show();

            Intent intent2 = new Intent(SettingPage.this, MainActivity.class);
            startActivity(intent2);


            TextView text = findViewById(R.id.editTextTextPersonName);
            String name = text.getText().toString();

            editor.putString("username", name);
            editor.apply();

        });
//        Button saved = findViewById(R.id.saveButton);
//        saved.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Toast clickToast = Toast.makeText(getApplicationContext(), "SAVED!!", Toast.LENGTH_LONG);
//                clickToast.show();
//                Intent intent2 = new Intent(SettingPage.this, MainActivity.class);
//                startActivity(intent2);
//
//            }
//
//        });

    }
}