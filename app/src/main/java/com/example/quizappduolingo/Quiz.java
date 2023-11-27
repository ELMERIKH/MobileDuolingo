package com.example.quizappduolingo;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Quiz extends AppCompatActivity {

    private TextView mQuestionTextView;
    private RadioButton mOptionARadioButton;
    private RadioButton mOptionBRadioButton;
    private RadioButton mOptionCRadioButton;
    private RadioButton mOptionDRadioButton;
    private RadioGroup mOptionsRadioGroup;
    private Button mSubmitButton;
private MediaStore.Audio.Radio pr;
    private FirebaseFirestore db;

    public static  final String EXTRA_LANG = "com.example.quizappduolingo.EXTRA_LANG";
public int n;
    private FirebaseAuth firebaseAuth;
    private List<Question> mQuestionList;
    private static int mScore;
    private ProgressBar  mProgressBar;
    private int mQuestionIndex;
    private String userId;
    FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        getSupportActionBar().hide();


        Button quitbtn = findViewById(R.id.quitbtn);

        mQuestionTextView = findViewById(R.id.question);
        mOptionARadioButton = findViewById(R.id.radioButton1);
        mOptionBRadioButton = findViewById(R.id.radioButton2);
        mOptionCRadioButton = findViewById(R.id.radioButton3);
        mOptionDRadioButton = findViewById(R.id.radioButton4);
        mProgressBar = findViewById(R.id.progress_bar);




        mOptionsRadioGroup = findViewById(R.id.radiogroup);
        mSubmitButton = findViewById(R.id.submitbtn);

        db = FirebaseFirestore.getInstance();
        mQuestionList = new ArrayList<>();
        mScore = 0;
        mQuestionIndex = 0;


        loadQuestionsFromFirestore();
        firebaseAuth = FirebaseAuth.getInstance();


        userId = firebaseAuth.getCurrentUser().getUid();
        DocumentReference userDocRef = db.collection("users").document(userId);
        userDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    Intent intent = getIntent();
                    String lang = intent.getStringExtra("language");
                    if(lang.equals("English")){
                        Object rank = documentSnapshot.get("rankUS");
                        n= rank.hashCode();
                    }
                    if(lang.equals("Arabic")){
                        Object rank = documentSnapshot.get("rankAR");
                        n= rank.hashCode();
                    }
                    if(lang.equals("French")){
                        Object rank = documentSnapshot.get("rankFR");
                        n= rank.hashCode();
                    }
                    if(lang.equals("Russian")){
                        Object rank = documentSnapshot.get("rankRU");
                        n= rank.hashCode();
                    }

                } else {
                    Log.d("QuizActivity", "No such document");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("ProfileActivity", "Error getting document: " + e.getMessage());
            }
        });


        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = mOptionsRadioGroup.getCheckedRadioButtonId();

                if (selectedId == -1) {
                    // no option selected
                    Toast.makeText(Quiz.this, "Please select an option", Toast.LENGTH_SHORT).show();
                    return;
                }
                RadioButton selectedOption = findViewById(selectedId);
                String selectedText = selectedOption.getText().toString();

                Question currentQuestion = mQuestionList.get(mQuestionIndex);
                if (selectedText.equals(currentQuestion.getAnswer())) {
                    // correct answer
                    mScore++;

                } else {
                    // incorrect answer



                    if(Menu.lvl.equals("hard")){
                        Toast.makeText(Quiz.this, "sorry you failed", Toast.LENGTH_SHORT).show();
                        score();
                    }
                }

                mQuestionIndex++;

                if (mQuestionIndex < mQuestionList.size()) {
                    loadQuestion();
                } else { score();
                }
            }
        });

        quitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
private void score(){
            endQuiz();

            Intent intent = getIntent();
            String lang = intent.getStringExtra("language");


            if (n<mScore){


                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                DocumentReference userDocRef = mFirestore.collection("users").document(userId);
                if(lang.equals("English")){
                    Log.d(TAG, "score: " + n);
                    userDocRef.update("rankUS", mScore);
                }
                else if(lang.equals("Arabic")){
                    Log.d(TAG, "score: " + n);
                    userDocRef.update("rankAR", mScore);
                }
                else if(lang.equals("Russian")){
                    Log.d(TAG, "score: " + n);
                    userDocRef.update("rankRU", mScore);
                }
                else if(lang.equals("French")){
                    Log.d(TAG, "score: " + n);
                    userDocRef.update("rankFR", mScore);
                }

            }

    }
    private void loadQuestionsFromFirestore() {
        Intent intent = getIntent();
        String lang = intent.getStringExtra("language");
        Log.d(TAG, "Language: " + lang);
        db.collection(lang)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Question question = document.toObject(Question.class);
                                mQuestionList.add(question);
                            }

                            loadQuestion();
                        } else {
                            Toast.makeText(Quiz.this, "Error getting questions", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void loadQuestion() {
        Question question = mQuestionList.get(mQuestionIndex);

        mQuestionTextView.setText(question.getQuestion());
        mOptionARadioButton.setText(question.getOptionA());
        mOptionBRadioButton.setText(question.getOptionB());
        mOptionCRadioButton.setText(question.getOptionC());
        mOptionDRadioButton.setText(question.getOptionD());

        mOptionsRadioGroup.clearCheck();
        mProgressBar.setProgress((mQuestionIndex + 1) * 100 / mQuestionList.size());

    }

    private void endQuiz() {
        Intent intent = new Intent(Quiz.this, ResultActivity.class);
        intent.putExtra(ResultActivity.EXTRA_SCORE, mScore);
        intent.putExtra(ResultActivity.EXTRA_TOTAL_QUESTIONS, mQuestionList.size());
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

