package com.example.quizappduolingo;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileActivity extends AppCompatActivity {



        private TextView emailTextView, usernameTextView, rankTextView;
        private FirebaseAuth firebaseAuth;
        private FirebaseFirestore firestoreDB;
        private String userId;

        private Button backbtn;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_profile);
            getSupportActionBar().hide();




            emailTextView = findViewById(R.id.Email);
            usernameTextView = findViewById(R.id.Username);
            rankTextView = findViewById(R.id.Rank);

            firebaseAuth = FirebaseAuth.getInstance();
            firestoreDB = FirebaseFirestore.getInstance();

            userId = firebaseAuth.getCurrentUser().getUid();


            backbtn = findViewById(R.id.back);


            DocumentReference userDocRef = firestoreDB.collection("users").document(userId);
            userDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        String email = documentSnapshot.getString("email");
                        String username = documentSnapshot.getString("username");

                        Object rankAR = documentSnapshot.get("rankAR");
                        Object rankRU = documentSnapshot.get("rankRU");
                        Object rankFR = documentSnapshot.get("rankFR");
                        Object rankUS = documentSnapshot.get("rankUS");


                        float  rus=rankAR.hashCode();
                        float  rru=rankRU.hashCode();
                        float  rfr=rankUS.hashCode();
                        float  rar=rankFR.hashCode();
                        float r=(rus+rru+rfr+rar)/4;
                        userDocRef.update("rank", r);
                        Object rank = documentSnapshot.get("rank");
                        String ranking=rank.toString();
                        emailTextView.setText(email);
                        usernameTextView.setText(username);
                        rankTextView.setText(ranking);
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

            backbtn.setOnClickListener(new View.OnClickListener() {
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