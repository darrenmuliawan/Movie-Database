package edu.illinois.finalproject;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    final String DEFAULT_URL = "https://api.themoviedb.org/3/movie/now_playing?" +
            "page=1&language=en-US&api_key=71f617f7828962b265163541921f2037";
    final String UPCOMING_URL = "https://api.themoviedb.org/3/movie/upcoming?page=1&" +
            "language=en-US&api_key=71f617f7828962b265163541921f2037";
    final String NOWPLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?" +
            "page=1&language=en-US&api_key=71f617f7828962b265163541921f2037";
    final String POPULAR_URL = "https://api.themoviedb.org/3/movie/popular?" +
            "page=1&language=en-US&api_key=71f617f7828962b265163541921f2037";
    final String TOPRATED_URL = "https://api.themoviedb.org/3/movie/top_rated?" +
            "page=1&language=en-US&api_key=71f617f7828962b265163541921f2037";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button nowPlaying = (Button) findViewById(R.id.nowPlayingButton);
        final Button upcoming = (Button) findViewById(R.id.upcomingButton);
        final Button popular = (Button) findViewById(R.id.popularButton);
        final Button topRated = (Button) findViewById(R.id.topRatedButton);
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.movie_list);

        populateRecyclerView(recyclerView, DEFAULT_URL);
        nowPlaying.setBackgroundColor(Color.DKGRAY);
        nowPlaying.setTextColor(Color.WHITE);

        nowPlaying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                populateRecyclerView(recyclerView, NOWPLAYING_URL);
                nowPlaying.setBackgroundColor(Color.DKGRAY);
                nowPlaying.setTextColor(Color.WHITE);
                upcoming.setBackgroundColor(Color.GRAY);
                upcoming.setTextColor(Color.BLACK);
                popular.setBackgroundColor(Color.GRAY);
                popular.setTextColor(Color.BLACK);
                topRated.setBackgroundColor(Color.GRAY);
                topRated.setTextColor(Color.BLACK);
            }
        });

        upcoming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                populateRecyclerView(recyclerView, UPCOMING_URL);
                upcoming.setBackgroundColor(Color.DKGRAY);
                upcoming.setTextColor(Color.WHITE);
                nowPlaying.setBackgroundColor(Color.GRAY);
                nowPlaying.setTextColor(Color.BLACK);
                popular.setBackgroundColor(Color.GRAY);
                popular.setTextColor(Color.BLACK);
                topRated.setBackgroundColor(Color.GRAY);
                topRated.setTextColor(Color.BLACK);
            }
        });

        popular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                populateRecyclerView(recyclerView, POPULAR_URL);
                popular.setBackgroundColor(Color.DKGRAY);
                popular.setTextColor(Color.WHITE);
                upcoming.setBackgroundColor(Color.GRAY);
                upcoming.setTextColor(Color.BLACK);
                nowPlaying.setBackgroundColor(Color.GRAY);
                nowPlaying.setTextColor(Color.BLACK);
                topRated.setBackgroundColor(Color.GRAY);
                topRated.setTextColor(Color.BLACK);
            }
        });

        topRated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                populateRecyclerView(recyclerView, TOPRATED_URL);
                topRated.setBackgroundColor(Color.DKGRAY);
                topRated.setTextColor(Color.WHITE);
                upcoming.setBackgroundColor(Color.GRAY);
                upcoming.setTextColor(Color.BLACK);
                popular.setBackgroundColor(Color.GRAY);
                popular.setTextColor(Color.BLACK);
                nowPlaying.setBackgroundColor(Color.GRAY);
                nowPlaying.setTextColor(Color.BLACK);
            }
        });
    }

    public void populateRecyclerView(RecyclerView recyclerView, String url) {
        List<Movie> movieList = new ArrayList<>();
        MoviesAdapter moviesAdapter = new MoviesAdapter(movieList);
        recyclerView.setAdapter(moviesAdapter);

        MoviesAsyncTask moviesAsyncTask = new MoviesAsyncTask(this,
                moviesAdapter, movieList);
        moviesAsyncTask.execute(url);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
    }
}
