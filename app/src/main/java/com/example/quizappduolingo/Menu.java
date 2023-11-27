package com.example.quizappduolingo;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Menu extends AppCompatActivity {

    private static String language;
     static String lvl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        getSupportActionBar().hide();
        lvl =getIntent().getStringExtra("levels");


        ImageView img = findViewById(R.id.langimg);
        int iconId = getIntent().getIntExtra("iconId", -1);
        if (iconId != -1) {
            img.setImageResource(iconId);
        }


        Button startbtn = findViewById(R.id.startbtn);
        Button leavebtn = findViewById(R.id.leavebtn);

        leavebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        startbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                String lang = intent.getStringExtra("language");


                FirebaseFirestore db = FirebaseFirestore.getInstance();
                language =lang;
                CollectionReference questionsRef = db.collection(lang);
                questionsRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<Question> questionList = new ArrayList<>();
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Question question = documentSnapshot.toObject(Question.class);
                            questionList.add(question);
                        }
                        startQuiz(questionList);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "Error getting questions from Firestore", e);
                    }
                });
            }
        });
    }

    private void startQuiz(List<Question> questionList) {
        Intent intent = new Intent(Menu.this, Quiz.class);
        System.out.println("jjjjjjjjjjjjj"+lvl);
        intent.putExtra("language", language);
        intent.putExtra("questionList", (Serializable) questionList);
        intent.putExtra("levels", lvl);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
