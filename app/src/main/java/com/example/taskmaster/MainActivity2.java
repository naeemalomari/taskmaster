package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {

    Integer count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        TextView task = findViewById(R.id.textView13);
        count = Integer.parseInt(task.getText().toString());

        Button button22 = findViewById(R.id.button3);

        button22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast clickToast = Toast.makeText(getApplicationContext(), "SUBMITTED!!", Toast.LENGTH_LONG);
                clickToast.show();
                count++;
                task.setText(String.valueOf(count));
            }
        });
    }
}