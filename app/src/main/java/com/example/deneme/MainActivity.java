package com.example.deneme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Collections;

public class MainActivity extends AppCompatActivity {


    private void startExam(){
        Intent intocan = new Intent(MainActivity.this,Quiz.class);
        startActivity(intocan);
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button startExam = (Button) findViewById(R.id.start1);
        startExam.setOnClickListener(startExamClick);




        }
        public View.OnClickListener startExamClick = new View.OnClickListener() {
            public void onClick(View v) {
                startExam();


            }
        };
    }

