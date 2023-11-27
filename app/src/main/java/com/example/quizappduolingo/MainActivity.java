package com.example.quizappduolingo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimatedImageDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView img;
    private ImageView imgtype;
    private static int SPLASH_TIME_OUT = 3000;
    private MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        mediaPlayer = MediaPlayer.create(this, R.raw.intro);
        img = findViewById(R.id.animlogo);
        imgtype = findViewById(R.id.animtype);
        mediaPlayer.start();
        Drawable drawable = img.getDrawable();
        if (drawable instanceof Animatable) {
            ((Animatable) drawable).start();
        }

        Drawable drawable2 = imgtype.getDrawable();
        if (drawable2 instanceof Animatable) {
            ((Animatable) drawable2).start();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent loginIntent = new Intent(MainActivity.this, Login.class);
                startActivity(loginIntent);
                finish();
            }
        }, SPLASH_TIME_OUT);

    }
}