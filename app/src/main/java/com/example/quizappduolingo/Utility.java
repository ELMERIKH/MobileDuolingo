package com.example.quizappduolingo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

public class Utility extends AppCompatActivity {

    static void showToast(Context context, String message) {
        //pour afficher un message popup le context continet toute les informations sur l'activité
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}