package com.example.flixster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Movie {
    String poster_path, backdrop_path, title, overview;

    public Movie(JSONObject jsonObject) throws JSONException {
        // Assigns the variables their values based on the JSON key
        poster_path = jsonObject.getString("poster_path");
        backdrop_path = jsonObject.getString("backdrop_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
    }

    // Iterates through the JSON array of movies and makes an array of Movie objects
    public static List<Movie> fromJsonArray(JSONArray movieJsonArray) throws JSONException {
        List<Movie> movies = new ArrayList<>();
        for(int i = 0; i < movieJsonArray.length(); i++) {
            movies.add(new Movie(movieJsonArray.getJSONObject(i)));
        }
        return movies;
    }

    public String getPoster_path() {
        return "https://image.tmdb.org/t/p/w342/" + poster_path;
    }

    public String getBackdrop_path() {
        return "https://image.tmdb.org/t/p/w342/" + backdrop_path;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }
}
