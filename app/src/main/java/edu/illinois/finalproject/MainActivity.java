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
    String NOWPLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?" +
            "page=1&language=en-US&api_key=71f617f7828962b265163541921f2037";
    String UPCOMING_URL = "https://api.themoviedb.org/3/movie/upcoming?page=1&" +
            "language=en-US&api_key=71f617f7828962b265163541921f2037";

    String POPULAR_URL = "https://api.themoviedb.org/3/movie/popular?" +
            "page=1&language=en-US&api_key=71f617f7828962b265163541921f2037";
    String TOPRATED_URL = "https://api.themoviedb.org/3/movie/top_rated?" +
            "page=1&language=en-US&api_key=71f617f7828962b265163541921f2037";
    String PAGE_FINDER_STRING = "?page=";
    int nowPlayingCtr = 1;
    int upcomingCtr = 1;
    int popularCtr = 1;
    int topRatedCtr = 1;
    List<Movie> nowPlayingMovieList = new ArrayList<>();
    List<Movie> upcomingMovieList = new ArrayList<>();
    List<Movie> popularMovieList = new ArrayList<>();
    List<Movie> topRatedMovieList = new ArrayList<>();

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
                if (nowPlaying.getCurrentTextColor() == Color.WHITE) {
                    NOWPLAYING_URL = nextPageUrl(NOWPLAYING_URL, nowPlayingCtr);
                    populateRecyclerView(recyclerView, NOWPLAYING_URL, nowPlayingMovieList);
                    nowPlayingCtr++;
                } else if (upcoming.getCurrentTextColor() == Color.WHITE) {
                    UPCOMING_URL = nextPageUrl(UPCOMING_URL, upcomingCtr);
                    populateRecyclerView(recyclerView, UPCOMING_URL, upcomingMovieList);
                    upcomingCtr++;
                } else if (popular.getCurrentTextColor() == Color.WHITE) {
                    POPULAR_URL = nextPageUrl(POPULAR_URL, popularCtr);
                    populateRecyclerView(recyclerView, POPULAR_URL, popularMovieList);
                    popularCtr++;
                } else if (topRated.getCurrentTextColor() == Color.WHITE) {
                    TOPRATED_URL = nextPageUrl(TOPRATED_URL, topRatedCtr);
                    populateRecyclerView(recyclerView, TOPRATED_URL, topRatedMovieList);
                    topRatedCtr++;
                }
            }
        });
    }

    /**
     * Edit the current page URL to become the next page URL.
     * @param url URL of current page.
     * @param currentPage current page.
     * @return URL of next page.
     */
    public String nextPageUrl(String url, int currentPage) {
        String nextPageUrl;
        String oldPageNumber = PAGE_FINDER_STRING + String.valueOf(currentPage);
        String newPageNumber = PAGE_FINDER_STRING + String.valueOf(currentPage + 1);
        nextPageUrl = url.replace(oldPageNumber, newPageNumber);
        return nextPageUrl;
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
