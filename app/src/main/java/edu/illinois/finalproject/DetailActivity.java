package edu.illinois.finalproject;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static android.content.ContentValues.TAG;

public class DetailActivity extends AppCompatActivity {
    public static final String GENRE_URL = "https://api.themoviedb.org/3/genre/movie/list?" +
            "language=en-US&api_key=71f617f7828962b265163541921f2037";
    public static final String IMAGE_URL = "https://image.tmdb.org/t/p/w500/";
    private FirebaseDatabase database;
    Calendar calendar = Calendar.getInstance();
    List<Comment> commentList = new ArrayList<>();
    public static TextView movieGenre;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movies_detail);

        final Intent intent = getIntent();
        final Movie movie = intent.getParcelableExtra(MoviesAdapter.MOVIE);
        final ImageView movieImage = (ImageView) findViewById(R.id.imageView);
        final TextView movieName = (TextView) findViewById(R.id.title);
        movieGenre = (TextView) findViewById(R.id.genre);
        final TextView movieOverview = (TextView) findViewById(R.id.overview);
        final TextView movieReleaseDate = (TextView) findViewById(R.id.releaseDate);
        final TextView movieRating = (TextView) findViewById(R.id.rating);
        final EditText nameCommentBox = (EditText) findViewById(R.id.nameCommentBox);
        final EditText commentBox = (EditText) findViewById(R.id.commentBox);
        final Button commentButton = (Button) findViewById(R.id.commentButton);
        final Button reminderButton = (Button) findViewById(R.id.reminderButton);
        final RecyclerView commentRecyclerView = (RecyclerView) findViewById(R.id.commentList);

        // Set the User Interface
        String imageUrl = IMAGE_URL + movie.getDetailImageUrl();
        Picasso.with(this).load(imageUrl).into(movieImage);

        movieName.setText(movie.getTitle());

        movieOverview.setText(movie.getOverview());
        //http://www.viralandroid.com/2015/10/how-to-make-scrollable-textview-in-android.html
        movieOverview.setMovementMethod(new ScrollingMovementMethod());

        String releaseDate = "Release date: " + movie.getReleaseDate();
        movieReleaseDate.setText(releaseDate);

        Helper.compareDates(movie, reminderButton);
        reminderButton.setText(("Remind me when " + movie.getTitle() + " is released!"));
        reminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = movie.getReleaseDate();
                String title = movie.getTitle() + " is released today!";
                try {
                    calendar = CalendarHelper.insertDateToCalendar(date);
                    addEvent(title, calendar);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        String rating = "Rating: " + Double.toString(movie.getRating());
        movieRating.setText(rating);

        // Set the text for movieGenre
        HashMap<Integer, String> genreMap = new HashMap<>();
        GenreAsyncTask genreAsyncTask = new GenreAsyncTask(movie, genreMap);
        genreAsyncTask.execute(GENRE_URL);

        // Writing to Firebase
        database = FirebaseDatabase.getInstance();
        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Helper.writeToDatabase(database, movie, commentBox, nameCommentBox);
                Toast.makeText(DetailActivity.this, "Comments added!",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Setting up the RecyclerView
        final CommentAdapter commentAdapter = new CommentAdapter(commentList);
        commentRecyclerView.setAdapter(commentAdapter);
        commentRecyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));

        // Read from the database to populate the commentRecyclerView
        DatabaseReference titleRef = database.getReference(movie.getTitle());
        DatabaseReference commentRef = titleRef.child(Helper.COMMENT_REF);
        Helper.readFromTheDatabase(commentAdapter, commentRef);
    }

    /**
     * Open Google Calendar to set the reminder for the event.
     * https://developer.android.com/guide/components/intents-common.html#Calendar
     * @param title title of event
     * @param calendar Calendar object which contains the date of the event
     */
    public void addEvent(String title, Calendar calendar) {
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.Events.TITLE, title)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, calendar.getTimeInMillis())
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, calendar.getTimeInMillis());

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
