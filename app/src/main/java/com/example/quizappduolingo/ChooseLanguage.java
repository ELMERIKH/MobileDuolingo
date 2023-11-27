package com.example.quizappduolingo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class ChooseLanguage extends AppCompatActivity {

    private  static String lang;
    private Button leavebtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_language);
        getSupportActionBar().hide();


        ImageButton russiabtn = (ImageButton) findViewById(R.id.russia);
        ImageButton usabtn = (ImageButton) findViewById(R.id.usa);
        ImageButton francebtn = (ImageButton) findViewById(R.id.france);
        ImageButton arabbtn = (ImageButton) findViewById(R.id.arab);
        leavebtn = findViewById(R.id.leavebtn);
        String lvl =getIntent().getStringExtra("levels");

        russiabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menuIntent = new Intent(ChooseLanguage.this, Menu.class);
                lang = "Russian";
                menuIntent.putExtra("language", lang);
                menuIntent.putExtra("iconId", R.drawable.russia);


                menuIntent.putExtra("levels", lvl);
                startActivity(menuIntent);
            }
        });


        usabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menuIntent = new Intent(ChooseLanguage.this, Menu.class);
                lang = "English";
                menuIntent.putExtra("language", lang);
                menuIntent.putExtra("iconId", R.drawable.usa);
                menuIntent.putExtra("levels", lvl);
                startActivity(menuIntent);
            }
        });


        francebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menuIntent = new Intent(ChooseLanguage.this, Menu.class);
                lang = "French";
                menuIntent.putExtra("language", lang);
                menuIntent.putExtra("iconId", R.drawable.france);
                menuIntent.putExtra("levels", lvl);
                startActivity(menuIntent);
            }
        });


        arabbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menuIntent = new Intent(ChooseLanguage.this, Menu.class);
                lang = "Arabic";
                menuIntent.putExtra("language", lang);
                menuIntent.putExtra("iconId", R.drawable.arab);
                menuIntent.putExtra("levels", lvl);

                startActivity(menuIntent);
            }
        });

        leavebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}