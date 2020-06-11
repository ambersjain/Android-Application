package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class CovidActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid);

        TextView countryTextView = findViewById(R.id.textView9);
        Intent intent = getIntent();
        String country = intent.getStringExtra("Countries");

        countryTextView.setText("Country :                     " + country);



    }

    public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(), CovidInfoActivity.class);
        intent.putExtra("url", "https://www.health.gov.au/news/health-alerts/novel-coronavirus-2019-ncov-health-alert");
        startActivity(intent);
    }
}
