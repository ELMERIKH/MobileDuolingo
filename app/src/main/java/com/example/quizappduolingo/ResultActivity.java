package com.example.quizappduolingo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    public static final String EXTRA_SCORE = "com.example.quizappduolingo.EXTRA_SCORE";
    public static final String EXTRA_TOTAL_QUESTIONS = "com.example.quizappduolingo.EXTRA_TOTAL_QUESTIONS";
    private TextView mScoreTextView;
    private Button mRestartButton;
    private ImageView Imagescore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        getSupportActionBar().hide();

        mScoreTextView = findViewById(R.id.score);
        mRestartButton = findViewById(R.id.leavemenu);
        Imagescore = findViewById(R.id.imagescore);

        int score = getIntent().getIntExtra(ResultActivity.EXTRA_SCORE, 0);
        mScoreTextView.setText("You Scored " + score + "/10 !");
        if (score < 5) {
            Imagescore.setImageResource(R.drawable.cry);
        } else if (score == 10) {

        } else {
            Imagescore.setImageResource(R.drawable.happy);
        }

        mRestartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, Dashboard.class);
                startActivity(intent);
                finish();
            }
        });
    }
}