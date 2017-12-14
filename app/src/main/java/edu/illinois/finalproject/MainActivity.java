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
    static Button nowPlaying;
    static Button upcoming;
    static Button popular;
    static Button topRated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nowPlaying = (Button) findViewById(R.id.nowPlayingButton);
        upcoming = (Button) findViewById(R.id.upcomingButton);
        popular = (Button) findViewById(R.id.popularButton);
        topRated = (Button) findViewById(R.id.topRatedButton);
        final Button loadMoreButton = (Button) findViewById(R.id.loadMoreButton);
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.movie_list);

        // Default main page
        Helper.populateRecyclerView(recyclerView, NOWPLAYING_URL, nowPlayingMovieList);
        nowPlaying.setBackgroundColor(Color.DKGRAY);
        nowPlaying.setTextColor(Color.WHITE);

        // click "Now Playing" button
        nowPlaying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Helper.populateRecyclerView(recyclerView, NOWPLAYING_URL, nowPlayingMovieList);
                Helper.updateButtonColor(nowPlaying, upcoming, popular, topRated);
                nowPlaying.setBackgroundColor(Color.DKGRAY);
                nowPlaying.setTextColor(Color.WHITE);
            }
        });

        // click "Upcoming" button
        upcoming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Helper.populateRecyclerView(recyclerView, UPCOMING_URL, upcomingMovieList);
                Helper.updateButtonColor(nowPlaying, upcoming, popular, topRated);
                upcoming.setBackgroundColor(Color.DKGRAY);
                upcoming.setTextColor(Color.WHITE);
            }
        });

        // click "Popular" button
        popular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Helper.populateRecyclerView(recyclerView, POPULAR_URL, popularMovieList);
                Helper.updateButtonColor(nowPlaying, upcoming, popular, topRated);
                popular.setBackgroundColor(Color.DKGRAY);
                popular.setTextColor(Color.WHITE);
            }
        });

        // click "Top Rated" button
        topRated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Helper.populateRecyclerView(recyclerView, TOPRATED_URL, topRatedMovieList);
                Helper.updateButtonColor(nowPlaying, upcoming, popular, topRated);
                topRated.setBackgroundColor(Color.DKGRAY);
                topRated.setTextColor(Color.WHITE);
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
