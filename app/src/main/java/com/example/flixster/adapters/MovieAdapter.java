package com.example.flixster.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixster.R;
import com.example.flixster.models.Movie;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import uk.co.deanwild.flowtextview.FlowTextView;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    public static final String TAG = "MovieAdapter";

    Context context;
    List<Movie> movies;

    // MovieAdapter constuctor
    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    // Inflates a layout from XML into a View, and returns that ViewHolder
    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Log.i(TAG, "onCreateViewHolder called");

        // Inflate the layout into a View
        View movieView = LayoutInflater.from(context).inflate(R.layout.movie_item_layout, parent, false);

        // Wrap the View in a ViewHolder and return it
        ViewHolder viewHolder = new ViewHolder(movieView);
        return viewHolder;
    }

    // Populates movie data at a given position into the View via its ViewHolder
    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Log.i(TAG, "onBindViewHolder called with position " + position);

        // Get the movie at the given position
        Movie movie = movies.get(position);

        // Bind the movie data into the ViewHolder
        holder.bind(movie);
    }

    // Returns the number of items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    // Create new ViewHolder class
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView posterImageView;
        //TextView titleTextView;
        //TextView overviewTextView;
        FlowTextView flowTextView;

        // Viewholder constructor
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            posterImageView = itemView.findViewById(R.id.posterImageView);
            //titleTextView = itemView.findViewById(R.id.titleTextView);
            //overviewTextView = itemView.findViewById(R.id.overviewTextView);
            flowTextView = itemView.findViewById((R.id.flowTextView));
        }

        // Binds a movie into the ViewHolder
        public void bind(Movie movie) {
            String imagePath;
            int placeholderImage;

            // Find whether to load the poster or backdrop based on orientation
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) imagePath = movie.getPoster_path();
            else imagePath = movie.getBackdrop_path();

            // Find which placeholder image to use based on orientation
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) placeholderImage = R.drawable.flicks_movie_placeholder;
            else placeholderImage = R.drawable.flicks_backdrop_placeholder;

            Glide.with(context)
                    .load(imagePath)
                    .placeholder(placeholderImage)
                    .into(posterImageView);
            //titleTextView.setText(movie.getTitle());
            //overviewTextView.setText(movie.getOverview());
            flowTextView.setText(movie.getTitle().toUpperCase() + "\n" + movie.getOverview());
        }
    }
}
