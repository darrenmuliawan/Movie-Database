package edu.illinois.finalproject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by darrenalexander on 12/5/17.
 */

public class MoviesAsyncTask extends AsyncTask<String, Integer, MoviesCollection> {
    public static final String TAG = MoviesAsyncTask.class.getSimpleName();
    private Context context;
    private MoviesAdapter moviesAdapter;
    List<MoviesCollection> restaurantArray;

    /**
     * Constructor for AsyncTask
     * @param context
     * @param moviesAdapter
     * @param movieList
     */
    public MoviesAsyncTask(Context context,
                           MoviesAdapter moviesAdapter, List movieList) {
        this.context = context;
        this.moviesAdapter = moviesAdapter;
        this.restaurantArray = movieList;
    }

    @Override
    protected MoviesCollection doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);

            URLConnection connection = url.openConnection();
            connection.setRequestProperty("user-key", ApiKey.API_KEY);
            connection.setRequestProperty("accept", "application/json");
            connection.connect();

            InputStream inStream = connection.getInputStream();
            InputStreamReader inStreamReader = new InputStreamReader(inStream,
                    Charset.forName("UTF-8"));

            Gson gson = new Gson();
            MoviesCollection moviesCollection = gson.fromJson(inStreamReader,
                    MoviesCollection.class);

            return moviesCollection;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(MoviesCollection moviesCollection) {
        if (moviesCollection == null) {
            return;
        }
        for (Movies movies: moviesCollection.getMovies()) {
            Log.d(TAG, "Movie: " + movies.getMovie().getTitle());
            moviesAdapter.addMovies(movies.getMovie());
        }
        moviesAdapter.notifyDataSetChanged();
    }
}
