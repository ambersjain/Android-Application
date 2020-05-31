package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void onClick(View view) {

        Log.i("Info", "================================Button Pressed===============================");
        //Need to get the value of text put in the input field which we get from id name


        EditText editText = (EditText) findViewById(R.id.editText);

        //Can't leave the input field empty so:
        if (editText.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Please enter an email", Toast.LENGTH_SHORT).show();
        }
        else {
            Log.i("Values", editText.getText().toString());

            // Can make this more readable using if statements
            Toast.makeText(this, editText.getText().toString() , Toast.LENGTH_SHORT).show();
        }

    }

}
