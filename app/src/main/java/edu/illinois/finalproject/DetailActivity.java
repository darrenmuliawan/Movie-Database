package edu.illinois.finalproject;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.util.Date;

public class DetailActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movies_detail);

        final Intent intent = getIntent();
        final Movie movie = intent.getParcelableExtra(MoviesAdapter.MOVIE);
        //final ImageView movieImage = (ImageView) findViewById(R.id.imageView);
        final TextView movieName = (TextView) findViewById(R.id.title);
        final TextView movieGenre = (TextView) findViewById(R.id.genre);
        final TextView movieOverview = (TextView) findViewById(R.id.overview);
        final TextView movieReleaseDate = (TextView) findViewById(R.id.releaseDate);
        final TextView movieRating = (TextView) findViewById(R.id.rating);
        final EditText commentBox = (EditText) findViewById(R.id.commentBox);
        final Button commentButton = (Button) findViewById(R.id.commentButton);
        final Button reminderButton = (Button) findViewById(R.id.reminderButton);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date1 = sdf.parse(movie.getReleaseDate());
            Date date2 = new Date(System.currentTimeMillis());
            if (date1.after(date2)) {
                reminderButton.setVisibility(View.VISIBLE);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //String imageUrl = "https://image.tmdb.org/t/p/w500/" + movie.getImageUrl();
        //Picasso.with(this).load(imageUrl).into(movieImage);
        movieName.setText(movie.getTitle());
        movieOverview.setText(movie.getOverview());
        //http://www.viralandroid.com/2015/10/how-to-make-scrollable-textview-in-android.html
        movieOverview.setMovementMethod(new ScrollingMovementMethod());
        movieReleaseDate.setText(movie.getReleaseDate());

        String rating = "Rating: " + Double.toString(movie.getRating());
        movieRating.setText(rating);

        String genre = "";
        for(int i = 0; i < movie.getGenre().length; i++) {
            if (i == movie.getGenre().length - 1) {
                genre = genre + movie.getGenre()[i];
            } else {
                genre = genre + movie.getGenre()[i] + ", ";
            }
        }
        movieGenre.setText(genre);
    }
}
