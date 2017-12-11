package edu.illinois.finalproject;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.provider.CalendarContract;
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
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static android.content.ContentValues.TAG;

public class DetailActivity extends AppCompatActivity {
    private FirebaseDatabase database;
    Calendar calendar = Calendar.getInstance();
    final String COMMENT_REF = "comments";

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movies_detail);

        final Intent intent = getIntent();
        final Movie movie = intent.getParcelableExtra(MoviesAdapter.MOVIE);
        final ImageView movieImage = (ImageView) findViewById(R.id.imageView);
        final TextView movieName = (TextView) findViewById(R.id.title);
        final TextView movieGenre = (TextView) findViewById(R.id.genre);
        final TextView movieOverview = (TextView) findViewById(R.id.overview);
        final TextView movieReleaseDate = (TextView) findViewById(R.id.releaseDate);
        final TextView movieRating = (TextView) findViewById(R.id.rating);
        final EditText commentBox = (EditText) findViewById(R.id.commentBox);
        final Button commentButton = (Button) findViewById(R.id.commentButton);
        final Button reminderButton = (Button) findViewById(R.id.reminderButton);
        final RecyclerView commentRecyclerView = (RecyclerView) findViewById(R.id.commentList);

        //https://stackoverflow.com/questions/19109960/how-to-check-if-a-date-is-greater-than-
        // another-in-java
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date1 = sdf.parse(movie.getReleaseDate());
            //https://stackoverflow.com/questions/5175728/how-to-get-the-current-date-time-in-java
            Date date2 = new Date(System.currentTimeMillis());
            if (date1.after(date2)) {
                reminderButton.setVisibility(View.VISIBLE);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String imageUrl = "https://image.tmdb.org/t/p/w500/" + movie.getDetailImageUrl();
        Picasso.with(this).load(imageUrl).into(movieImage);
        movieName.setText(movie.getTitle());
        movieOverview.setText(movie.getOverview());
        //http://www.viralandroid.com/2015/10/how-to-make-scrollable-textview-in-android.html
        movieOverview.setMovementMethod(new ScrollingMovementMethod());

        String releaseDate = "Release date: " + movie.getReleaseDate();
        movieReleaseDate.setText(releaseDate);

        StringBuilder reminderButtonText = new StringBuilder();
        reminderButtonText.append("Remind me when " + movie.getTitle() + " is released!");
        reminderButton.setText(reminderButtonText.toString());
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

        String genre = "Genre: ";
        for(int i = 0; i < movie.getGenre().length; i++) {
            if (i == movie.getGenre().length - 1) {
                genre = genre + movie.getGenre()[i];
            } else {
                genre = genre + movie.getGenre()[i] + ", ";
            }
        }
        movieGenre.setText(genre);

        // DATABASE
        database = FirebaseDatabase.getInstance();
        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference titleRef = database.getReference(movie.getTitle());
                DatabaseReference commentsRef = titleRef.child(COMMENT_REF);
                String comment = commentBox.getText().toString();
                String time = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").
                        format(Calendar.getInstance().getTime());

                Comment newComment = new Comment("alexander", comment, time);
                commentsRef.push().setValue(newComment);

                // set up a counter that is expecting 1 down count
                final CountDownLatch writeSignal = new CountDownLatch(1);
                // prevent forward progress until count is zero or 2 seconds pass
                try {
                    writeSignal.await(2, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Toast.makeText(DetailActivity.this, "Comments added!",
                        Toast.LENGTH_SHORT).show();
            }
        });

        List<Comment> commentList = new ArrayList<>();
        final CommentAdapter commentAdapter = new CommentAdapter(commentList);
        commentRecyclerView.setAdapter(commentAdapter);

        // populating the commentRecyclerView
        DatabaseReference titleRef = database.getReference(movie.getTitle());
        DatabaseReference myRef = titleRef.child(COMMENT_REF);

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String comment = null;
                String name = null;
                String time = null;
                Comment commentObj = new Comment(null, null, null);
                commentAdapter.deleteAllComment();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                        if (dataSnapshot2.getKey().equals("comment")) {
                            comment = String.valueOf(dataSnapshot2.getValue());
                        }
                        if (dataSnapshot2.getKey().equals("name")) {
                            name = String.valueOf(dataSnapshot2.getValue());
                        }
                        if (dataSnapshot2.getKey().equals("time")) {
                            time = String.valueOf(dataSnapshot2.getValue());
                        }
                        commentObj = new Comment(name, comment, time);
                    }
                    commentAdapter.addComment(commentObj);
                    commentAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.d(TAG, "ASDF");
            }
        });
        commentRecyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
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
