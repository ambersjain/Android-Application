package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class CovidActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid);

        TextView countryTextView = findViewById(R.id.textView9);
        TextView textView10 = findViewById(R.id.textView10);
        TextView textView11 = findViewById(R.id.textView11);
        TextView textView12 = findViewById(R.id.textView12);
        TextView textView13 = findViewById(R.id.textView13);
        TextView textView14 = findViewById(R.id.textView14);

        Intent intent = getIntent();

        String country = intent.getStringExtra("Countries");
        String NewConfirmed = intent.getStringExtra("NewConfirmed");
        String TotalConfirmed = intent.getStringExtra("TotalConfirmed");
        String TotalDeaths = intent.getStringExtra("TotalDeaths");
        String TotalRecovered = intent.getStringExtra("TotalRecovered");
        String date = intent.getStringExtra("Date");

        countryTextView.setText("Country : " + country);
        textView10.setText("New Confirmed : " + NewConfirmed);
        textView11.setText("Total Confirmed : " + TotalConfirmed);
        textView12.setText("Total Deaths : " + TotalDeaths);
        textView13.setText("Total Recovered : " + TotalRecovered);
        textView14.setText("Date : " + date);
    }
}
