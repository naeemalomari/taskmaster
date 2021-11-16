package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.amplifyframework.core.Amplify;

import java.io.File;
import java.util.prefs.PreferenceChangeEvent;

public class TaskDetailPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail_page);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String taskName = extras.getString("title");
            TextView text = findViewById(R.id.textView4);
            text.setText(taskName);

            String taskBody = extras.getString("body");
            TextView text2 = findViewById(R.id.textView5);
            text2.setText(taskBody);

            String taskState = extras.getString("state");
            TextView text3 = findViewById(R.id.state2);
            text3.setText(taskState);

//        Intent intent = getIntent();
//        ((TextView) findViewById(R.id.textView4)).setText(intent.getExtras().getString("title"));
//        ((TextView) findViewById(R.id.textView5)).setText(intent.getExtras().getString("body"));
//        ((TextView) findViewById(R.id.state2)).setText(intent.getExtras().getString("state"));

            String img = extras.getString("img");
            Amplify.Storage.downloadFile(
                    "image",
                    new File(getApplicationContext().getFilesDir() + "/download.jpg"),
                    result -> {
                        ImageView image = findViewById(R.id.imgeViewIdDetail);
                        extras.getString("img");
                        image.setImageBitmap(BitmapFactory.decodeFile(result.getFile().getPath()));

                        Log.i("MyAmplifyApp", "Successfully downloaded: " + result.getFile());
                    },
                    error -> Log.e("MyAmplifyApp", "Download Failure", error)
            );
        }
    }

}
