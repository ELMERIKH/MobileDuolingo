package com.example.quizappduolingo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.io.Serializable;

public class DifficultyActivity extends AppCompatActivity {
private ImageButton hardbtn,ezbtn;
private Button quitbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty);
        getSupportActionBar().hide();

        hardbtn = findViewById(R.id.hard);
        ezbtn = findViewById(R.id.easy);
        quitbtn = findViewById(R.id.quitbtn);

        quitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        hardbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DifficultyActivity.this, ChooseLanguage.class);
                intent.putExtra("levels", "hard");
                startActivity(intent);
            }
    });
        ezbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DifficultyActivity.this, ChooseLanguage.class);
                intent.putExtra("levels", "easy");
                startActivity(intent);
            }
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}