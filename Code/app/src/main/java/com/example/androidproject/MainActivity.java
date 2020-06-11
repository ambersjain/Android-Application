package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText emailEditText = findViewById(R.id.emailEditText);
        final EditText passwordEditText = findViewById(R.id.passwordEditText);
        Button loginButton = findViewById(R.id.loginButton);
        Button signupButton = findViewById(R.id.signUpbutton);

        // Need to check whether login information exists or not
        final DatabaseHelper db = new DatabaseHelper(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result = db.fetchUser(emailEditText.getText().toString(), passwordEditText.getText().toString());

                if (result == true)
                {
                    Toast.makeText(MainActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
                    Intent optionsIntent = new Intent(MainActivity.this, OptionsActivity.class);
                    //myIntent.putExtra("key", value); //Optional parameters
                    startActivity (optionsIntent);
                }
                else {
                    Toast.makeText(MainActivity.this, "Email not found" , Toast.LENGTH_SHORT).show();
                }

            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }
        });

    }


//
//    public void onClickLogin(View view) {
//
//        Log.i("Info", "================================Button Pressed===============================");
//        //Need to get the value of text put in the input field which we get from id name
//
//        EditText emailEditText = findViewById(R.id.emailEditText);
//        // Can't leave the input field empty so:
//        if (emailEditText.getText().toString().isEmpty())
//        {
//            Toast.makeText(this, "Please enter an email", Toast.LENGTH_SHORT).show();
//        }
//        else {
//            Log.i("Values", emailEditText.getText().toString());
//
//            // Can make this more readable using if statements
//            Toast.makeText(this, emailEditText.getText().toString() , Toast.LENGTH_SHORT).show();
//        }
//
//    }

}