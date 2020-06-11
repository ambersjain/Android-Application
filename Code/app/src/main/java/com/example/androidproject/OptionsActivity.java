package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class OptionsActivity extends AppCompatActivity {

    // Creating a list to store countries
    ArrayList<String> options = new ArrayList<>();
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        DownloadData task = new DownloadData();
        try {
            task.execute("https://api.covid19api.com/summary");
        } catch (Exception e) {

        }

        ListView listView = findViewById(R.id.countriesListView);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, options);
        listView.setAdapter(arrayAdapter);

    }

    public class DownloadData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String json = "";
            URL url;
            HttpURLConnection httpURLConnection;

            try {
                url = new URL(urls[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                int data = inputStreamReader.read();
                while (data != -1) {
                    char currentChar = (char) data;
                    json += currentChar;
                    data = inputStreamReader.read();
                }

                JSONObject jsonObject = new JSONObject(json);
                JSONArray jsonArray = jsonObject.getJSONArray("Countries");
                Log.i("Countries", String.valueOf(jsonArray));

                //Log.i("Countries", String.valueOf(jsonArray));
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonElement = jsonArray.getJSONObject(i);
                    Log.i("Country", jsonElement.getString("Country"));
                    Log.i("NewConfirmed", jsonElement.getString("NewConfirmed"));
                    Log.i("TotalConfirmed", jsonElement.getString("TotalConfirmed"));
                    Log.i("TotalDeaths", jsonElement.getString("TotalDeaths"));
                    Log.i("TotalRecovered", jsonElement.getString("TotalRecovered"));
                    Log.i("Date", jsonElement.getString("Date"));
                }
                return json;
            } catch (IOException | JSONException e) {
                e.printStackTrace();
                return null;
            }

        }
    }
}
