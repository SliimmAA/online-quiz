package com.asma.onlinequiz;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class StartActivity extends AppCompatActivity {

    // selectedTopicName will be assigned by a topic name ('java', 'android', 'html', 'php')
    private String selectedTopicName = "";

    //private String titlename = "OnlineQuiz";
    int RC_SIGN_IN = 0;
    SignInButton signin;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_start_activity);

        //Toolbar toolbar = findViewById(R.id.toolbar);
        //TextView toolbar_title = toolbar.findViewById(R.id.toolbar_title);
        //toolbar_title.setText(titlename);


        // implenting google sign in
        signin = findViewById(R.id.sign_in_button);
        signin.setOnClickListener(view -> {
            switch (view.getId()) {
                case R.id.sign_in_button:
                    signIn();
                    break;
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        // Initialize widgets from activity_splash_screen.xml
        final Button startBtn = findViewById(R.id.startQuizBtn);
        final LinearLayout javaLayout = findViewById(R.id.javaLayout);
        final LinearLayout phpLayout = findViewById(R.id.phpLayout);
        final LinearLayout htmlLayout = findViewById(R.id.htmlLayout);
        final LinearLayout androidLayout = findViewById(R.id.androidLayout);

        javaLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // assign java to selectedTopicName
                selectedTopicName = "java";

                // select java layout
                javaLayout.setBackgroundResource(R.drawable.round_back_white_stroke10);

                // de-select other layouts
                phpLayout.setBackgroundResource(R.drawable.round_back_white10);
                htmlLayout.setBackgroundResource(R.drawable.round_back_white10);
                androidLayout.setBackgroundResource(R.drawable.round_back_white10);
            }
        });

        phpLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // assign php to selectedTopicName
                selectedTopicName = "php";

                // select php layout
                phpLayout.setBackgroundResource(R.drawable.round_back_white_stroke10);

                // de-select other layouts
                javaLayout.setBackgroundResource(R.drawable.round_back_white10);
                htmlLayout.setBackgroundResource(R.drawable.round_back_white10);
                androidLayout.setBackgroundResource(R.drawable.round_back_white10);
            }
        });

        htmlLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // assign html to selectedTopicName
                selectedTopicName = "html";

                // select HTML layout
                htmlLayout.setBackgroundResource(R.drawable.round_back_white_stroke10);

                // de-select other layouts
                javaLayout.setBackgroundResource(R.drawable.round_back_white10);
                phpLayout.setBackgroundResource(R.drawable.round_back_white10);
                androidLayout.setBackgroundResource(R.drawable.round_back_white10);
            }
        });

        androidLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // assign android to selectedTopicName
                selectedTopicName = "android";

                // select Android layout
                androidLayout.setBackgroundResource(R.drawable.round_back_white_stroke10);

                // de-select other layouts
                javaLayout.setBackgroundResource(R.drawable.round_back_white10);
                phpLayout.setBackgroundResource(R.drawable.round_back_white10);
                htmlLayout.setBackgroundResource(R.drawable.round_back_white10);
            }
        });


        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* if user not selected any topic yet then show a Toast message
                 * selectedTopicName will be empty or default value ("") if user not selected any topic yet*/
                if (selectedTopicName.isEmpty()) {
                    Toast.makeText(StartActivity.this, "Please select topic first", Toast.LENGTH_SHORT).show();
                } else {

                    // Create an Object of Intent to open quiz questions screen
                    final Intent intent = new Intent(StartActivity.this, MainActivity.class);

                    //put user entered name and selected topic name to intent for use in next activity
                    intent.putExtra("selectedTopic", selectedTopicName);

                    // call startActivity to open next activity along with data(userName, selectedTopicName)
                    startActivity(intent);

                    finish(); // finish (destroy) this activity
                }
            }
        });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            Intent intent1 = new Intent(StartActivity.this,Login.class);
            startActivity(intent1);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Error", "signInResult:failed code=" + e.getStatusCode());
        }
    }


}