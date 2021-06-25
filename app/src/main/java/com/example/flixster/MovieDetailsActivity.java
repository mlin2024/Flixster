package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.databinding.ActivityMovieDetailsBinding;
import com.example.flixster.models.Movie;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import okhttp3.Headers;

public class MovieDetailsActivity<ButtonView> extends AppCompatActivity {
    public static final String TAG = "MovieDetailsActivity";
    public String VIDEO_API_URL_1 = "https://api.themoviedb.org/3/movie/";
    public String VIDEO_API_URL_2 = "/videos?api_key=";

    Context context;
    Movie movie;

    ImageView movieDetailsImageView;
    YouTubePlayerFragment movieDetailsYouTubeFragment;
    TextView movieDetailsTitleTextView;
    RatingBar movieDetailsVoteAvgRatingBar;
    TextView movieDetailsOverviewTextView;
    Button movieDetailsBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.context = this;
        VIDEO_API_URL_2 += getString(R.string.tmdb_api_key);

        // Use ViewBinding library to reference views
        ActivityMovieDetailsBinding binding = ActivityMovieDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Unwrap the movie that was passed in by the intent
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d(TAG, "Showing details for " + movie.getTitle());

        String imagePath;
        int placeholderImage;

        // Find whether to load the poster or backdrop based on orientation
        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) imagePath = movie.getPoster_path();
        else imagePath = movie.getBackdrop_path();

        // Find which placeholder image to use based on orientation
        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) placeholderImage = R.drawable.flicks_movie_placeholder;
        else placeholderImage = R.drawable.flicks_backdrop_placeholder;

        // Set the views to the appropriate values
        Glide.with(context)
                .load(imagePath)
                .placeholder(placeholderImage)
                .centerCrop() // scale image to fill the entire ImageView
                .transform(new RoundedCornersTransformation(30, 10))
                .into(binding.movieDetailsImageView);
        binding.movieDetailsTitleTextView.setText(movie.getTitle());
        binding.movieDetailsVoteAvgRatingBar.setRating((float) movie.getVote_average() * 0.5f);
        binding.movieDetailsOverviewTextView.setText(movie.getOverview());

        // End this activity when back button clicked
        binding.movieDetailsBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Get youtube video info
        movieDetailsYouTubeFragment = (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.movieDetailsYouTubeFragment);
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(VIDEO_API_URL_1 + movie.getId() + VIDEO_API_URL_2, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                Log.i(TAG, "onSuccess called");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i(TAG, "Results: "+ results.toString());

                    // Go through the results and get the first youtube video
                    boolean found = false;
                    for (int q = 0; q < results.length(); q++) {
                        if (results.getJSONObject(q).getString("site").equals("YouTube")) {
                            found = true;
                            String youtubeKey = results.getJSONObject(q).getString("key");
                            movieDetailsYouTubeFragment.initialize(getString(R.string.youtube_api_key),
                                    new YouTubePlayer.OnInitializedListener() {
                                        @Override
                                        public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                                            YouTubePlayer youTubePlayer, boolean b) {
                                            // do any work here to cue video, play video, etc.
                                            youTubePlayer.cueVideo(youtubeKey);
                                        }
                                        @Override
                                        public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

                                        }
                                    });
                            break;
                        }
                    }
                    if (!found) {
                        Log.e(TAG, "Movie has no YouTube trailer");
                        Toast.makeText(getApplicationContext(), "Movie has no YouTube trailer", Toast.LENGTH_SHORT).show();
                    }
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