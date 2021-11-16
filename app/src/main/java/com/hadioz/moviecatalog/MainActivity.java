package com.hadioz.moviecatalog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MovieAdapter adapter;
    private ArrayList<Movie> movies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataLoad load = new DataLoad();
        load.execute();
        recyclerView = (RecyclerView) findViewById(R.id.rcView);
        adapter = new MovieAdapter(movies, this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_one, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.aboutOpt:
                Log.d("about", "clicked");
                Intent intentAbout = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intentAbout);
                return true;

            case R.id.languageOpt:
                Intent intentLang = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(intentLang);
                Log.d("language", "clicked");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public class DataLoad extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... strings) {
            String current = "";
            try {
                URL url;
                HttpURLConnection connection = null;

            try {
                url = new URL("https://api.themoviedb.org/3/trending/all/week?api_key=872d959e6acd6468ade85a250abbe190");
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setDoOutput(true);
                connection.connect();

                BufferedReader buff = new BufferedReader( new InputStreamReader( url.openStream()));
                StringBuffer stringBuffer = new StringBuffer();
                String line;
                while ((line = buff.readLine()) != null) {
                    stringBuffer.append(line + "\n");
                }
                buff.close();

                current = stringBuffer.toString();
                // return the data to onPostExecute method
                return current;

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
            }  catch (Exception e) {
                e.printStackTrace();
                return "Exception: " + e.getMessage();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                Log.d("data", s);
                JSONObject result = new JSONObject(s);
                JSONArray arrayJSON = result.getJSONArray("results");
                Log.d("length :", String.valueOf(arrayJSON.length()));
                for (int i = 0; i < arrayJSON.length(); i++) {
                    JSONObject object = (JSONObject) arrayJSON.get(i);

                    String name = object.has("title") == true ? object.getString("title") : object.getString("name");
                    String release_date = object.has("release_date") == true ? object.getString("release_date") : object.getString("first_air_date");

                    Log.d("title", name);
                    Movie movie = new Movie(object.getInt("id"), name, object.getString("overview"), release_date, object.getString("poster_path"), object.getString("vote_average"));
                    movies.add(movie);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            adapter.notifyDataSetChanged();
        }
    }
}


