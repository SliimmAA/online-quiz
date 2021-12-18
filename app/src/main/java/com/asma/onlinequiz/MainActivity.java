package com.asma.onlinequiz;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.common.SignInButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {



    // total quiz time in minutes
    private int totalTimeInMins = 1;

    // Timer class object for countdown timer
    private Timer quizTimer;
    private int seconds = 0; // current countdown seconds

    // questions array list
    private List<QuestionsList> questionsLists = new ArrayList<>();

    // Current questions index position from  questionsLists ArrayList.
    private int currentQuestionPosition = 0;

    // Options
    private AppCompatButton option1, option2, option3, option4;

    // next button
    private AppCompatButton nextBtn;

    // Total questions and main question TextView
    private TextView question;
    private TextView questions;

    // selectedOption's Value. if user not selected any option yet then it is empty by default
    private String selectedOptionByUser = "";

    SignInButton btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //getSupportActionBar().hide();
        //hide title
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);


        // initialize widgets from activity_main.xml file
        final ImageView backBtn = findViewById(R.id.backBtn);
        final TextView topicName = findViewById(R.id.topicName);
        final TextView timer = findViewById(R.id.timer);
        question = findViewById(R.id.question);
        questions = findViewById(R.id.questions);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        nextBtn = findViewById(R.id.nextButton);

        // get Topic Name and User Name from StartActivity via Intent
        final String getTopicName = getIntent().getStringExtra("selectedTopic");

        // set Topic Name to TextView
        topicName.setText(getTopicName);

        // start quiz countdown timer
        startTimer(timer);

        // get questions from QuestionsBank class according to selectedTopicName and assign to questionsLists ArrayList
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://onlinequiz-app-3de8d-default-rtdb.firebaseio.com/");

        // show dialog while questions are being fetched
        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                // getting all questions from firebase database for a sepcific topic
                for (DataSnapshot dataSnapshot : snapshot.child(getTopicName).getChildren()){

                    // getting data from firebase database
                    final String getQuestion = dataSnapshot.child("question").getValue(String.class);
                    final String getOption1 = dataSnapshot.child("option1").getValue(String.class);
                    final String getOption2 = dataSnapshot.child("option2").getValue(String.class);
                    final String getOption3 = dataSnapshot.child("option3").getValue(String.class);
                    final String getOption4 = dataSnapshot.child("option4").getValue(String.class);
                    final String getAnswer = dataSnapshot.child("answer").getValue(String.class);


                    //adding data to the questionlist
                    QuestionsList questionsList = new QuestionsList(getQuestion,getOption1,getOption2,getOption3,getOption4,getAnswer);
                    questionsLists.add(questionsList);

                }

                // hide dialog
                progressDialog.hide();

                // set current question to TextView along with options from questionsLists ArrayList
                questions.setText((currentQuestionPosition + 1) + "/" + questionsLists.size());
                question.setText(questionsLists.get(currentQuestionPosition).getQuestion());
                option1.setText(questionsLists.get(currentQuestionPosition).getOption1());
                option2.setText(questionsLists.get(currentQuestionPosition).getOption2());
                option3.setText(questionsLists.get(currentQuestionPosition).getOption3());
                option4.setText(questionsLists.get(currentQuestionPosition).getOption4());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //questionsLists = QuestionsBank.getQuestions(getTopicName);



        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // check if user has not attempted this question yet
                if (selectedOptionByUser.isEmpty()) {

                    selectedOptionByUser = option1.getText().toString();

                    // change selected AppCompatButton background color and text color
                    option1.setBackgroundResource(R.drawable.round_back_red10);
                    option1.setTextColor(Color.WHITE);

                    // reveal answer
                    revealAnswer();

                    // assign user answer value to userSelectedOption in QuestionsList class
                    questionsLists.get(currentQuestionPosition).setUserSelectedOption(selectedOptionByUser);
                }
            }
        });

        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // check if user has not attempted this question yet
                if (selectedOptionByUser.isEmpty()) {

                    selectedOptionByUser = option2.getText().toString();

                    // change selected AppCompatButton background color and text color
                    option2.setBackgroundResource(R.drawable.round_back_red10);
                    option2.setTextColor(Color.WHITE);

                    // reveal answer
                    revealAnswer();

                    // assign user answer value to userSelectedOption in QuestionsList class
                    questionsLists.get(currentQuestionPosition).setUserSelectedOption(selectedOptionByUser);
                }
            }
        });

        option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // check if user has not attempted this question yet
                if (selectedOptionByUser.isEmpty()) {

                    selectedOptionByUser = option3.getText().toString();

                    // change selected AppCompatButton background color and text color
                    option3.setBackgroundResource(R.drawable.round_back_red10);
                    option3.setTextColor(Color.WHITE);

                    // reveal answer
                    revealAnswer();

                    // assign user answer value to userSelectedOption in QuestionsList class
                    questionsLists.get(currentQuestionPosition).setUserSelectedOption(selectedOptionByUser);
                }
            }
        });

        option4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // check if user has not attempted this question yet
                if (selectedOptionByUser.isEmpty()) {

                    selectedOptionByUser = option4.getText().toString();

                    // change selected AppCompatButton background color and text color
                    option4.setBackgroundResource(R.drawable.round_back_red10);
                    option4.setTextColor(Color.WHITE);

                    // reveal answer
                    revealAnswer();

                    // assign user answer value to userSelectedOption in QuestionsList class
                    questionsLists.get(currentQuestionPosition).setUserSelectedOption(selectedOptionByUser);
                }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // cancel timer
                quizTimer.purge();
                quizTimer.cancel();

                // open StartActivity.java
                startActivity(new Intent(MainActivity.this, StartActivity.class));
                finish(); // finish(destroy) this activity
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // check if user has not selected any option yet
                if (selectedOptionByUser.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please select an option", Toast.LENGTH_SHORT).show();
                } else {
                    changeNextQuestion();
                }
            }
        });
    }

    private void startTimer(TextView timerTextView) {

        quizTimer = new Timer();
        quizTimer.schedule(new TimerTask() {
            @Override
            public void run() {

                if (seconds == 0) {
                    totalTimeInMins--;
                    seconds = 59;
                } else if (seconds == 0 && totalTimeInMins == 0) {
                    // cancel timer
                    quizTimer.purge();
                    quizTimer.cancel();
                    Toast.makeText(MainActivity.this, "Timer Over", Toast.LENGTH_SHORT).show();

                    // Open result activity along with correct and incorrect answers
                    Intent intent = new Intent(MainActivity.this, QuizResults.class);
                    intent.putExtra("correct", getCorrectAnswers());
                    intent.putExtra("incorrect", getIncorrectAnswers());
                    startActivity(intent);

                    // finish(destroy) this activity
                    finish();
                } else {
                    seconds--;
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        String finalMinutes = String.valueOf(totalTimeInMins);
                        String finalSeconds = String.valueOf(seconds);

                        // check if minutes has only one digit(Ex. 9) then attach 0 before the digit to make it 09
                        if (finalMinutes.length() == 1) {
                            finalMinutes = "0" + finalMinutes;
                        }

                        // check if seconds has only one digit(Ex. 9) then attach 0 before the digit to make it 09
                        if (finalSeconds.length() == 1) {
                            finalSeconds = "0" + finalSeconds;
                        }

                        timerTextView.setText(finalMinutes + ":" + finalSeconds);
                    }
                });
            }
        }, 1000, 1000);
    }

    private void revealAnswer() {

        // get answer of current question
        final String getAnswer = questionsLists.get(currentQuestionPosition).getAnswer();

        // change background color and text color of option which match with answer
        if (option1.getText().toString().equals(getAnswer)) {
            option1.setBackgroundResource(R.drawable.round_back_green10);
            option1.setTextColor(Color.WHITE);
        } else if (option2.getText().toString().equals(getAnswer)) {
            option2.setBackgroundResource(R.drawable.round_back_green10);
            option2.setTextColor(Color.WHITE);
        } else if (option3.getText().toString().equals(getAnswer)) {
            option3.setBackgroundResource(R.drawable.round_back_green10);
            option3.setTextColor(Color.WHITE);
        } else if (option4.getText().toString().equals(getAnswer)) {
            option4.setBackgroundResource(R.drawable.round_back_green10);
            option4.setTextColor(Color.WHITE);
        }
    }

    private void changeNextQuestion() {

        // increment currentQuestionPosition by 1 for next question
        currentQuestionPosition++;

        // change next button text to submit if it is last question
        if ((currentQuestionPosition + 1) == questionsLists.size()) {
            nextBtn.setText("Submit Quiz");
        }

        // check if next question is available. else quiz completed
        if (currentQuestionPosition < questionsLists.size()) {

            // make selectedOptionByUser empty to hold next question's answer
            selectedOptionByUser = "";

            // set normal background color and text color to options
            option1.setBackgroundResource(R.drawable.round_back_white_stroke2_10);
            option1.setTextColor(Color.parseColor("#1F6BB8"));
            option2.setBackgroundResource(R.drawable.round_back_white_stroke2_10);
            option2.setTextColor(Color.parseColor("#1F6BB8"));
            option3.setBackgroundResource(R.drawable.round_back_white_stroke2_10);
            option3.setTextColor(Color.parseColor("#1F6BB8"));
            option4.setBackgroundResource(R.drawable.round_back_white_stroke2_10);
            option4.setTextColor(Color.parseColor("#1F6BB8"));

            // set current question to TextView along with options from questionsLists ArrayList
            questions.setText((currentQuestionPosition + 1) + "/" + questionsLists.size());
            question.setText(questionsLists.get(currentQuestionPosition).getQuestion());
            option1.setText(questionsLists.get(currentQuestionPosition).getOption1());
            option2.setText(questionsLists.get(currentQuestionPosition).getOption2());
            option3.setText(questionsLists.get(currentQuestionPosition).getOption3());
            option4.setText(questionsLists.get(currentQuestionPosition).getOption4());
        } else {

            // Open result activity along with correct and incorrect answers
            Intent intent = new Intent(MainActivity.this, QuizResults.class);
            intent.putExtra("correct", getCorrectAnswers());
            intent.putExtra("incorrect", getIncorrectAnswers());
            startActivity(intent);

            // finish(destroy) this activity
            finish();
        }
    }

    private int getCorrectAnswers() {

        int correctAnswers = 0;

        for (int i = 0; i < questionsLists.size(); i++) {
            final String getUserSelectedOption = questionsLists.get(i).getUserSelectedOption();
            final String getAnswer = questionsLists.get(i).getAnswer();

            // compare user selected option with original answer
            if (getUserSelectedOption.equals(getAnswer)) {
                correctAnswers++;
            }
        }
        return correctAnswers;
    }

    private int getIncorrectAnswers() {

        int incorrectAnswers = 0;

        for (int i = 0; i < questionsLists.size(); i++) {
            final String getUserSelectedOption = questionsLists.get(i).getUserSelectedOption();
            final String getAnswer = questionsLists.get(i).getAnswer();

            // compare user selected option with original answer
            if (!getUserSelectedOption.equals(getAnswer)) {
                incorrectAnswers++;
            }
        }
        return incorrectAnswers;
    }

    @Override
    public void onBackPressed() {
        // cancel timer
        quizTimer.purge();
        quizTimer.cancel();

        // open StartActivity.java
        startActivity(new Intent(MainActivity.this, StartActivity.class));
        finish(); // finish(destroy) this activity
    }
}