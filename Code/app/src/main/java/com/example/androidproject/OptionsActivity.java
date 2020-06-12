package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
    ArrayList<String> countries = new ArrayList<>();
    ArrayList<String> newConfirmed = new ArrayList<>();
    ArrayList<String> totalConfirmed = new ArrayList<>();
    ArrayList<String> totalDeaths = new ArrayList<>();
    ArrayList<String> totalRecovered = new ArrayList<>();
    ArrayList<String> date = new ArrayList<>();
    ArrayList<String> global = new ArrayList<>();

    ArrayAdapter arrayAdapter;

    // Creating a database to store the countrywise Covid Data
    SQLiteDatabase covidDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        covidDB = this.openOrCreateDatabase("Covid", MODE_PRIVATE, null);

        // create the table
        covidDB.execSQL("CREATE TABLE IF NOT EXISTS covidTable (Country VARCHAR PRIMARY KEY, NewConfirmed VARCHAR, TotalConfirmed VARCHAR, TotalDeaths VARCHAR, TotalRecovered VARCHAR, Date VARCHAR )");


        DownloadData task = new DownloadData();
        try {
            task.execute("https://api.covid19api.com/summary");
        } catch (Exception e) {

        }

        ListView listView = findViewById(R.id.countriesListView);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, countries);
        listView.setAdapter(arrayAdapter);

        // transfering this data to other activity
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), CovidActivity.class);
                intent.putExtra("Countries", countries.get(position));
                intent.putExtra("NewConfirmed", newConfirmed.get(position));
                intent.putExtra("TotalConfirmed", totalConfirmed.get(position));
                intent.putExtra("TotalDeaths", totalDeaths.get(position));
                intent.putExtra("TotalRecovered", totalRecovered.get(position));
                intent.putExtra("Date", date.get(position));

                startActivity(intent);
            }
        });

        updateListView();


    }

    public void updateListView() {
        // Get stuff out of the database

        Cursor c = covidDB.rawQuery("SELECT * FROM covidTable", null);

        int countryIndex = c.getColumnIndex("Country");
        int newConfirmedIndex = c.getColumnIndex("NewConfirmed");
        int totalConfirmedIndex = c.getColumnIndex("TotalConfirmed");
        int totalDeathsIndex = c.getColumnIndex("TotalDeaths");
        int totalRecoveredIndex = c.getColumnIndex("TotalRecovered");
        int dateIndex = c.getColumnIndex("Date");

        if (c.moveToFirst()) {
            countries.clear();
            newConfirmed.clear();
            totalRecovered.clear();
            totalConfirmed.clear();
            totalDeaths.clear();
            date.clear();

            do {
                countries.add(c.getString(countryIndex));
                newConfirmed.add(c.getString(newConfirmedIndex));
                totalRecovered.add(c.getString(totalRecoveredIndex));
                totalConfirmed.add(c.getString(totalConfirmedIndex));
                totalDeaths.add(c.getString(totalDeathsIndex));
                date.add(c.getString(dateIndex));

            } while (c.moveToNext());

            arrayAdapter.notifyDataSetChanged();

        }

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

                // Delete the previous table before creating a new one
                covidDB.execSQL("DELETE FROM covidTable");
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonElement = jsonArray.getJSONObject(i);
                    if (!jsonElement.isNull("Country") && !jsonElement.isNull("NewConfirmed") && !jsonElement.isNull("TotalConfirmed") && !jsonElement.isNull("TotalDeaths") && !jsonElement.isNull("TotalRecovered") && !jsonElement.isNull("Date")) {

                        String country = jsonElement.getString("Country");
                        String newConfirmed = jsonElement.getString("NewConfirmed");
                        String totalConfirmed = jsonElement.getString("TotalConfirmed");
                        String totalDeaths = jsonElement.getString("TotalDeaths");
                        String totalRecovered = jsonElement.getString("TotalRecovered");
                        String date = jsonElement.getString("Date");

                        // Put all these json elements into the SQL table

                        String sql = "INSERT INTO covidTable (Country, NewConfirmed, TotalConfirmed, TotalDeaths, TotalRecovered, Date) VALUES (?, ?, ?, ?, ?, ?)";
                        SQLiteStatement statement = covidDB.compileStatement(sql);
                        statement.bindString(1, country);
                        statement.bindString(2, newConfirmed);
                        statement.bindString(3, totalConfirmed);
                        statement.bindString(4, totalDeaths);
                        statement.bindString(5, totalRecovered);
                        statement.bindString(6, date);

                        statement.execute();
                    }
                }
                return json;
            } catch (IOException | JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            updateListView();
        }
    }
}
