package com.example.patriciaouyang.instagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {

    private EditText usernameInput;
    private EditText passwordInput;
    private Button loginBtn;
    private Button signUpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            // do stuff with the user
            final Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        } else {
            // show the signup or login screen
            setContentView(R.layout.activity_main);

            usernameInput = findViewById(R.id.userName);
            passwordInput = findViewById(R.id.password);
            loginBtn = findViewById(R.id.btnLogin);
            signUpBtn = findViewById(R.id.btnSignUp);

            loginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final String username = usernameInput.getText().toString();
                    final String password = passwordInput.getText().toString();

                    login(username, password);
                }
            });

            signUpBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    signup();
                }
            });
        }
    }

    private void login(String username, String password) {
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e == null) {
                    Log.d("LoginActivity", "Login successful!");

                    final Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Log.e("LoginActivity", "Login failure.");
                    e.printStackTrace();
                }
            }
        });
    }

    private void signup() {
        Intent i = new Intent (MainActivity.this, SignUpActivity.class);
        startActivity(i);
        finish();
    }
}

