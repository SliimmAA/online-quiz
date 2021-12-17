package com.asma.onlinequiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class QuizResults extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_quiz_results);


        final TextView correctAnswers = findViewById(R.id.correctAnswers);
        final TextView incorrectAnswers = findViewById(R.id.incorrectAnswers);
        final Button startNewQuizBtn = findViewById(R.id.startNewQuizButton);

        // Get Correct and Incorrect answers from MainActivity.class via intent
        final int getCorrectAnswers = getIntent().getIntExtra("correct",0);
        final int getInCorrectAnswers = getIntent().getIntExtra("incorrect",0);

        // set correct and incorrect answers to TextViews
        correctAnswers.setText("Correct Answers : "+getCorrectAnswers);
        incorrectAnswers.setText("Incorrect Answers : "+getInCorrectAnswers);

        startNewQuizBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(QuizResults.this, StartActivity.class));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(QuizResults.this, StartActivity.class));
        finish();
    }
}