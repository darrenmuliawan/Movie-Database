package edu.illinois.finalproject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;

/**
 * Created by darrenalexander on 12/11/17.
 */

public class GenreAsyncTask extends AsyncTask<String, Integer, HashMap<Integer, String>> {
    public static final String TAG = GenreAsyncTask.class.getSimpleName();
    HashMap<Integer, String> genreMap;
    Movie movie;
    String url = "https://api.themoviedb.org/3/genre/movie/list?language=en-US&api_key=71f617f7828962b265163541921f2037";

    public GenreAsyncTask(Movie movie, HashMap<Integer, String> genreMap) {
        this.movie = movie;
        this.genreMap = genreMap;
    }

    @Override
    protected HashMap<Integer, String> doInBackground(String... params) {
//        HttpHandler httpHandler = new HttpHandler();
//        String jsonStr = httpHandler.makeServiceCall(url);
//        if (jsonStr != null) {
//            try {
//                JSONObject jsonObject = new JSONObject(jsonStr);
//                JSONArray genres = jsonObject.getJSONArray("genres");
//                for (int i = 0; i < genres.length(); i++) {
//                    JSONObject genre = genres.getJSONObject(i);
//                    int id = genre.getInt("id");
//                    String name = genre.getString("name");
//                    HashMap<Integer, String> genreMap = new HashMap<>();
//                    genreMap.put(id, name);
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }

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
        //DetailActivity.movieGenre
//        if (genreCollection == null) {
//            Log.d(TAG, "NULL MOVIE");
//        }
//
//        for (Genre genre: genreCollection.getGenres()) {
//            Log.d(TAG, "Movie: " + genre.getId());
//            genreMap.put(genre.getId(), genre.getName());
//        }
    }
}
