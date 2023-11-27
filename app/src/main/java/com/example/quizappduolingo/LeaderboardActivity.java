package com.example.quizappduolingo;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LeaderboardActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    private Button BackB;
    private String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        getSupportActionBar().hide();
        BackB=findViewById(R.id.backB);
         db = FirebaseFirestore.getInstance();

        CollectionReference usersRef = db.collection("users");

        Query query = usersRef.orderBy("rank", Query.Direction.DESCENDING);

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<String> userIds = new ArrayList<>();
                for (DocumentSnapshot document : task.getResult()) {
                    String userId = document.getId();
                    userIds.add(userId);
                }
                displayLeaderboard(userIds);
            } else {
                Log.d(TAG, "Error getting documents: ", task.getException());
            }
        });}

        private void displayLeaderboard(List<String> userIds) {
            // Initialize a list of User objects to hold the user information
            List<UserClass> users = new ArrayList<>();

            // Retrieve the user information from Firestore for each user ID
            for (String userId : userIds) {
                DocumentReference userRef = db.collection("users").document(userId);
                userRef.get().addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String username = documentSnapshot.getString("username");
                        Object rank = documentSnapshot.get("rank");
                       String r=rank.toString();
                        float rf = Float.parseFloat(r);

                        UserClass user = new UserClass(username, rf);
                        users.add(user);

                        // If we have retrieved information for all users, display the leaderboard
                        if (users.size() == userIds.size()) {
                            // Sort the users by rank in descending order
                            Collections.sort(users, (u1, u2) ->  Float.compare(u2.getRank(), u1.getRank()));

                            // Display the leaderboard in a RecyclerView or ListView
                            // Here's an example using a RecyclerView and a custom adapter:
                            RecyclerView recyclerView = findViewById(R.id.recyclerView);
                            recyclerView.setLayoutManager(new LinearLayoutManager(this));
                            LeaderboardAdapter adapter = new LeaderboardAdapter(users);
                            recyclerView.setAdapter(adapter);
                        }
                    }
                }).addOnFailureListener(e -> {
                    Log.d(TAG, "Error getting user information: ", e);
                });

            }
            BackB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }


}

