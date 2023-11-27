package com.example.quizappduolingo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Dashboard extends AppCompatActivity {

    private ImageButton quizbtn, scorebtn,leaderbtn;
    private ImageView profil;
    private TextView name;
    private Button logoutbtn;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestoreDB;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getSupportActionBar().hide();


        quizbtn = findViewById(R.id.quizbtn);
        logoutbtn = findViewById(R.id.logoutbtn);
        scorebtn = findViewById(R.id.scorebtn);
        profil = findViewById(R.id.profile);
        name = findViewById(R.id.name);
        leaderbtn=findViewById(R.id.leaderboardbtn);

        firebaseAuth = FirebaseAuth.getInstance();
        firestoreDB = FirebaseFirestore.getInstance();

        userId = firebaseAuth.getCurrentUser().getUid();

        DocumentReference userDocRef = firestoreDB.collection("users").document(userId);
        userDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    String username = documentSnapshot.getString("username");
                    name.setText("Welcome, " + username);
                } else {
                    Log.d("ProfileActivity", "No such document");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("ProfileActivity", "Error getting document: " + e.getMessage());
            }
        });

        quizbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, DifficultyActivity.class);
                startActivity(intent);
            }
        });
        leaderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, LeaderboardActivity.class);
                startActivity(intent);
            }
        });


        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        scorebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, ScoreActivity.class);
                startActivity(intent);
            }
        });
        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
    }
}