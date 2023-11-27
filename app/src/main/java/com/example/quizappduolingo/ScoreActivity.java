package com.example.quizappduolingo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ScoreActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private TextView rankUS,rankAR,rankRU,rankFR;
    private FirebaseFirestore firestoreDB;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        getSupportActionBar().hide();

        rankUS = findViewById(R.id.rankUS);
        rankAR = findViewById(R.id.rankAR);
        rankRU = findViewById(R.id.rankRU);
        rankFR = findViewById(R.id.rankFR);

        firebaseAuth = FirebaseAuth.getInstance();
        firestoreDB = FirebaseFirestore.getInstance();

        Button leavebtn = findViewById(R.id.leavebtn);

        leavebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        userId = firebaseAuth.getCurrentUser().getUid();

        DocumentReference userDocRef = firestoreDB.collection("users").document(userId);
        userDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {

                    Object rankus = documentSnapshot.get("rankUS");
                    Object rankar = documentSnapshot.get("rankAR");
                    Object rankfr = documentSnapshot.get("rankFR");
                    Object rankru = documentSnapshot.get("rankRU");
                    String us= rankus.toString();
                    String ar= rankar.toString();
                    String fr= rankfr.toString();
                    String ru= rankru.toString();

                    rankAR.setText(ar);
                    rankUS.setText(us);
                    rankFR.setText(fr);
                    rankRU.setText(ru);
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

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}