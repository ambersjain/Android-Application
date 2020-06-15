package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Sign Up information
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final EditText emailEditText = findViewById(R.id.signUpEmailEditText);
        final EditText passwordEditText = findViewById(R.id.signUpPasswordEditText);
        final EditText confirmPasswordEditText = findViewById(R.id.signUpPasswordEditText2);
        Button signupButton2 = findViewById(R.id.signUpButton2);

        db = new DatabaseHelper(this);

        signupButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = emailEditText.getText().toString().trim();
                String pwd = passwordEditText.getText().toString().trim();
                String confirmed_pwd = confirmPasswordEditText.getText().toString().trim();
                // Need to check if password matches confirm password
                if (pwd.equals(confirmed_pwd))
                {
                    // Create a new row in the database
                    long result = db.insert(user, pwd);
                    if (result > 0)
                    {
                        Toast.makeText(RegisterActivity.this, "Registered successfully!", Toast.LENGTH_LONG).show();
                        Intent backIntent = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(backIntent);
                    }
                    else
                    {
                        Toast.makeText(RegisterActivity.this, "Registration error!", Toast.LENGTH_LONG).show();
                    }

                }
                else
                {
                    Toast.makeText(RegisterActivity.this, "Two passwords do not match!!", Toast.LENGTH_LONG).show();
                }


            }
            });
        }

}

