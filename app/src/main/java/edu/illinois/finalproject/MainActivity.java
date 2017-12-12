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

    private String DEFAULT_URL = "https://api.themoviedb.org/3/movie/now_playing?" +
            "page=1&language=en-US&api_key=71f617f7828962b265163541921f2037";
    private String NOWPLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?" +
            "page=1&language=en-US&api_key=71f617f7828962b265163541921f2037";
    private String UPCOMING_URL = "https://api.themoviedb.org/3/movie/upcoming?page=1&" +
            "language=en-US&api_key=71f617f7828962b265163541921f2037";
    private String POPULAR_URL = "https://api.themoviedb.org/3/movie/popular?" +
            "page=1&language=en-US&api_key=71f617f7828962b265163541921f2037";
    private String TOPRATED_URL = "https://api.themoviedb.org/3/movie/top_rated?" +
            "page=1&language=en-US&api_key=71f617f7828962b265163541921f2037";
    private int nowPlayingCtr = 1;
    private int upcomingCtr = 1;
    private int popularCtr = 1;
    private int topRatedCtr = 1;
    private List<Movie> nowPlayingMovieList = new ArrayList<>();
    private List<Movie> upcomingMovieList = new ArrayList<>();
    private List<Movie> popularMovieList = new ArrayList<>();
    private List<Movie> topRatedMovieList = new ArrayList<>();
    static MainActivity mainActivity;

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

        // Default main page
        List<Movie> movieList = new ArrayList<>();
        Helper.populateRecyclerView(recyclerView, DEFAULT_URL, movieList);
        nowPlaying.setBackgroundColor(Color.DKGRAY);
        nowPlaying.setTextColor(Color.WHITE);

        // click "Now Playing" button
        nowPlaying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Helper.populateRecyclerView(recyclerView, NOWPLAYING_URL, nowPlayingMovieList);
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

        // click "Upcoming" button
        upcoming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Helper.populateRecyclerView(recyclerView, UPCOMING_URL, upcomingMovieList);
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

        // click "Popular" button
        popular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Helper.populateRecyclerView(recyclerView, POPULAR_URL, popularMovieList);
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

        // click "Top Rated" button
        topRated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Helper.populateRecyclerView(recyclerView, TOPRATED_URL, topRatedMovieList);
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

        // click "Load More Movies" button
        loadMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nowPlaying.getCurrentTextColor() == Color.WHITE) {
                    NOWPLAYING_URL = Helper.loadMoreMovies(NOWPLAYING_URL, nowPlayingCtr,
                            recyclerView, nowPlayingMovieList);
                    nowPlayingCtr++;
                } else if (upcoming.getCurrentTextColor() == Color.WHITE) {
                    UPCOMING_URL = Helper.loadMoreMovies(UPCOMING_URL, upcomingCtr, recyclerView,
                            upcomingMovieList);
                    upcomingCtr++;
                } else if (popular.getCurrentTextColor() == Color.WHITE) {
                    POPULAR_URL = Helper.loadMoreMovies(POPULAR_URL, popularCtr, recyclerView,
                            popularMovieList);
                    popularCtr++;
                } else if (topRated.getCurrentTextColor() == Color.WHITE) {
                    TOPRATED_URL = Helper.loadMoreMovies(TOPRATED_URL, topRatedCtr, recyclerView,
                            topRatedMovieList);
                    topRatedCtr++;
                }
            }
        });
    }
}
