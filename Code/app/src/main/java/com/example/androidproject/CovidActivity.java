package com.example.androidproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class CovidActivity extends AppCompatActivity {

    String newConfirmed;
    String totalConfirmed;
    String newDeaths;
    String TotalDeaths;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.covidhelp:
                Intent intent = new Intent(getApplicationContext(), CovidInfoActivity.class);
                intent.putExtra("url", "https://www.bing.com/covid?vert=newsvideos");
                startActivity(intent);
                // launch a new activity with web views
                return true;
            default:
                return false;
        }
    }

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
        TextView textView5 = findViewById(R.id.textView5);
        TextView textView6 = findViewById(R.id.textView6);
        TextView textView7 = findViewById(R.id.textView7);
        TextView textView8 = findViewById(R.id.textView8);

        Intent intent = getIntent();

        String country = intent.getStringExtra("Countries");
        String NewConfirmed = intent.getStringExtra("NewConfirmed");
        String TotalConfirmed = intent.getStringExtra("TotalConfirmed");
        String TotalDeaths = intent.getStringExtra("TotalDeaths");
        String TotalRecovered = intent.getStringExtra("TotalRecovered");
        String date = intent.getStringExtra("Date");


        String NewConfirmedGlobal = intent.getStringExtra("newConfirmedGlobal");
        String TotalConfirmedGlobal = intent.getStringExtra("totalConfirmedGlobal");
        String NewDeathsGlobal = intent.getStringExtra("newDeathsGlobal");
        String TotalDeathsGlobal = intent.getStringExtra("totalDeathsGlobal");

        countryTextView.setText("Country : " + country);
        textView10.setText("New Confirmed : " + NewConfirmed);
        textView11.setText("Total Confirmed : " + TotalConfirmed);
        textView12.setText("Total Deaths : " + TotalDeaths);
        textView13.setText("Total Recovered : " + TotalRecovered);
        textView14.setText("Last Updated : " + date);

        // Putting the previously updated values as the API sometimes throws null values as well
        if (NewConfirmedGlobal == null || TotalConfirmedGlobal == null || NewDeathsGlobal == null || TotalDeathsGlobal == null)
        {
            NewConfirmedGlobal = "Updating....";
            TotalConfirmedGlobal = "Updating...";
            NewDeathsGlobal = "Updating...";
            TotalDeathsGlobal = "Updating...";
        }

        textView5.setText("New Confirmed : " + NewConfirmedGlobal);
        textView6.setText("Total Confirmed : " + TotalConfirmedGlobal);
        textView7.setText("New Deaths  : " + NewDeathsGlobal);
        textView8.setText("Total Deaths : " + TotalDeathsGlobal);

    }
}