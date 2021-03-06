package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.adapters.MovieAdapter;
import com.example.flixster.databinding.ActivityMainBinding;
import com.example.flixster.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    public String NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=";

    List<Movie> movies;

    RecyclerView moviesRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NOW_PLAYING_URL += getString(R.string.tmdb_api_key);

        movies = new ArrayList<>();

        moviesRecyclerView = findViewById(R.id.moviesRecyclerView);

        // Create an Adapter
        MovieAdapter movieAdapter = new MovieAdapter(this, movies);

        // Set the adapter on the RecyclerView
        moviesRecyclerView.setAdapter(movieAdapter);

        // Set a layout manager on the RecyclerView
        moviesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(NOW_PLAYING_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                Log.i(TAG, "onSuccess called");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i(TAG, "Results: "+ results.toString());

                    // Turn the results JSON array into an ArrayList, and add these movies to the movie ArrayList
                    movies.addAll(Movie.fromJsonArray(results));
                    Log.i(TAG, "Movies: "+ movies.toString());

                    // Notify the adapter that the data set has changed
                    movieAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    Log.e(TAG, "JSON exception");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                Log.e(TAG, "onFailure called", throwable);
            }
        });
    }
}