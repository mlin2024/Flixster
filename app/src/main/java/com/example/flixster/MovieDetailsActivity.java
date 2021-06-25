package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;

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

import com.bumptech.glide.Glide;
import com.example.flixster.databinding.ActivityMovieDetailsBinding;
import com.example.flixster.models.Movie;

import org.parceler.Parcels;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieDetailsActivity<ButtonView> extends AppCompatActivity {
    public static final String TAG = "MovieDetailsActivity";

    Context context;
    Movie movie;

    ImageView movieDetailsImageView;
    TextView movieDetailsTitleTextView;
    RatingBar movieDetailsVoteAvgRatingBar;
    TextView movieDetailsOverviewTextView;
    Button movieDetailsBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.context = this;

        // Use ViewBinding library to reference views
        ActivityMovieDetailsBinding binding = ActivityMovieDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Unwrap the movie that was passed in by the intent
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d(TAG, "showing details for " + movie.getTitle());

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
    }
}