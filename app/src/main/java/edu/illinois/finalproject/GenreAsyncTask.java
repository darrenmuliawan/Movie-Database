package edu.illinois.finalproject;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.HashMap;

/**
 * Created by darrenalexander on 12/11/17.
 */

public class GenreAsyncTask extends AsyncTask<String, Integer, HashMap<Integer, String>> {
    HashMap<Integer, String> genreMap;
    Movie movie;

    public GenreAsyncTask(Movie movie, HashMap<Integer, String> genreMap) {
        this.movie = movie;
        this.genreMap = genreMap;
    }

    @Override
    protected HashMap<Integer, String> doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);

            URLConnection connection = url.openConnection();
            connection.connect();

            InputStream inStream = connection.getInputStream();
            InputStreamReader inStreamReader = new InputStreamReader(inStream,
                    Charset.forName("UTF-8"));

            Gson gson = new Gson();
            GenreCollection genreCollection = gson.fromJson(inStreamReader,
                    GenreCollection.class);
            for (Genre genre: genreCollection.getGenres()) {
                genreMap.put(genre.getId(), genre.getName());
            }
            return genreMap;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();}

        return null;
    }

    @Override
    protected void onPostExecute(HashMap<Integer, String> genreMap) {
        super.onPostExecute(genreMap);
        StringBuilder genreText = new StringBuilder("Genre: ");
        for(int i = 0; i < movie.getGenre().length; i++) {
            String genre = genreMap.get(movie.getGenre()[i]);
            if (i == movie.getGenre().length - 1) {
                genreText.append(genre);
            } else {
                genreText.append(genre).append(", ");
            }
        }
        DetailActivity.movieGenre.setText(genreText);
    }
}
