package com.example.flixster;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.databinding.ActivityMovieTrailerBinding;
import com.example.flixster.models.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import okhttp3.Headers;

public class MovieTrailerActivity extends YouTubeBaseActivity {
    public static final String TAG = "MovieTrailerActivity";

    YouTubePlayerView youTubePlayerView;
    Movie movie;
    String youtubeKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMovieTrailerBinding binding = ActivityMovieTrailerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Unwrap the movie that was passed in by the intent
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d(TAG, "Showing video for " + movie.getTitle());

        // Unwrap the youtube key that was passed in by the intent
        youtubeKey = (String) Parcels.unwrap(getIntent().getParcelableExtra(String.class.getSimpleName()));
        Log.d("MovieTrailerActivity", "Trying to load video >" + youtubeKey + "<");

        // initialize with API key stored in secrets.xml
        binding.youTubePlayerView.initialize(getString(R.string.youtube_api_key), new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.cueVideo(youtubeKey);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                // log the error
                Log.e("MovieTrailerActivity", "Error initializing YouTube player");
            }
        });
    }
}