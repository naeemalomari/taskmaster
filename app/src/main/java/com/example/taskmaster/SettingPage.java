package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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


            TextView text = findViewById(R.id.editTextTextPersonName);
            String name = text.getText().toString();
            Toast clickToast = Toast.makeText(getApplicationContext(), "SAVED!!", Toast.LENGTH_LONG);
            clickToast.show();
            RadioGroup radioGroup = findViewById(R.id.groupRadioButtonFromSettingId);
            RadioButton radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
            String radioString = radioButton.getText().toString();

            editor.putString("username", name);
            editor.putString("teamName", radioString);
            editor.apply();

            Intent intent2 = new Intent(SettingPage.this, MainActivity.class);
            startActivity(intent2);
        });
    }
}