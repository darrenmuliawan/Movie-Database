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

    String DEFAULT_URL = "https://api.themoviedb.org/3/movie/now_playing?" +
            "page=1&language=en-US&api_key=71f617f7828962b265163541921f2037";
    String UPCOMING_URL = "https://api.themoviedb.org/3/movie/upcoming?page=1&" +
            "language=en-US&api_key=71f617f7828962b265163541921f2037";
    String NOWPLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?" +
            "page=1&language=en-US&api_key=71f617f7828962b265163541921f2037";
    String POPULAR_URL = "https://api.themoviedb.org/3/movie/popular?" +
            "page=1&language=en-US&api_key=71f617f7828962b265163541921f2037";
    String TOPRATED_URL = "https://api.themoviedb.org/3/movie/top_rated?" +
            "page=1&language=en-US&api_key=71f617f7828962b265163541921f2037";
    String PAGE_FINDER_STRING = "?page=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button nowPlaying = (Button) findViewById(R.id.nowPlayingButton);
        final Button upcoming = (Button) findViewById(R.id.upcomingButton);
        final Button popular = (Button) findViewById(R.id.popularButton);
        final Button topRated = (Button) findViewById(R.id.topRatedButton);
        final Button loadMoreButton = (Button) findViewById(R.id.loadMoreButton);
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.movie_list);

        final List<Movie> nowPlayingMovieList = new ArrayList<>();
        // Default main page
        List<Movie> movieList = new ArrayList<>();
        populateRecyclerView(recyclerView, DEFAULT_URL, movieList);
        nowPlaying.setBackgroundColor(Color.DKGRAY);
        nowPlaying.setTextColor(Color.WHITE);

        nowPlaying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                populateRecyclerView(recyclerView, NOWPLAYING_URL, nowPlayingMovieList);

                // Change colors of buttons
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
                List<Movie> upcomingMovieList = new ArrayList<>();
                populateRecyclerView(recyclerView, UPCOMING_URL, upcomingMovieList);

                // Change colors of buttons
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
                List<Movie> popularMovieList = new ArrayList<>();
                populateRecyclerView(recyclerView, POPULAR_URL, popularMovieList);

                // Change colors of buttons
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
                List<Movie> topRatedMovieList = new ArrayList<>();
                populateRecyclerView(recyclerView, TOPRATED_URL, topRatedMovieList);

                // Change colors of buttons
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

        loadMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int nowPlayingCtr = 1;
                int upcomingCtr = 1;
                int popularCtr = 1;
                int topRatedCtr = 1;
                String newNowPlayingUrl = NOWPLAYING_URL;
                if (nowPlaying.getCurrentTextColor() == Color.WHITE) {
                    nowPlayingCtr++;
                    newNowPlayingUrl = nextPageUrl(newNowPlayingUrl, nowPlayingCtr);
                    NOWPLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?" +
                            "page=2&language=en-US&api_key=71f617f7828962b265163541921f2037";
                    populateRecyclerView(recyclerView, UPCOMING_URL, nowPlayingMovieList);
                } else if (upcoming.getCurrentTextColor() == Color.WHITE) {

                } else if (popular.getCurrentTextColor() == Color.WHITE) {

                } else if (topRated.getCurrentTextColor() == Color.WHITE) {

                }
           }
        });
    }

    public String nextPageUrl(String url, int currentPage) {
        String pageNumber = PAGE_FINDER_STRING + String.valueOf(currentPage);
        //int indexOfPageNumber = url.lastIndexOf(PAGE_FINDER_STRING) + 1;
        url.replace()
        if (currentPage < 10) {

        } else {

        }
        return url;
    }

    /**
     * Add the list of movies to the RecyclerView.
     * @param recyclerView Recycler View.
     * @param url The URL where the JSON is parsed from.
     * @param movieList List of movies.
     * @return movieList
     */
    public void populateRecyclerView(RecyclerView recyclerView, String url, List<Movie> movieList) {
        MoviesAdapter moviesAdapter = new MoviesAdapter(movieList);
        recyclerView.setAdapter(moviesAdapter);

        MoviesAsyncTask moviesAsyncTask = new MoviesAsyncTask(this,
                moviesAdapter, movieList);
        moviesAsyncTask.execute(url);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
    }
}
