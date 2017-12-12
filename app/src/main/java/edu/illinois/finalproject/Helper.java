package edu.illinois.finalproject;

import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by darrenalexander on 12/11/17.
 */

public class Helper {
    private final static String PAGE_FINDER_STRING = "?page=";
    public final static String COMMENT_REF = "comments";
    static String comment = null;
    static String name = null;
    static String time = null;
    static Comment commentObj = new Comment(null, null, null);

    /**
     * This function is to load more movies to the list and show it on the RecyclerView.
     * @param url URL
     * @param counter counter to determine the URL using other method
     * @param recyclerView RecyclerView
     * @param movieList List of movies
     * @return edited URL
     */
    public static String loadMoreMovies(String url, int counter, RecyclerView recyclerView,
                                        List<Movie> movieList) {
        url = nextPageUrl(url, counter);
        populateRecyclerView(recyclerView, url, movieList);
        return url;
    }

    /**
     * Edit the current page URL to become the next page URL.
     * @param url URL of current page.
     * @param currentPage current page.
     * @return URL of next page.
     */
    public static String nextPageUrl(String url, int currentPage) {
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
     */
    public static void populateRecyclerView(RecyclerView recyclerView, String url,
                                            List<Movie> movieList) {
        MoviesAdapter moviesAdapter = new MoviesAdapter(movieList);
        recyclerView.setAdapter(moviesAdapter);

        MoviesAsyncTask moviesAsyncTask = new MoviesAsyncTask(MainActivity.mainActivity,
                moviesAdapter, movieList);
        moviesAsyncTask.execute(url);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.mainActivity,
                LinearLayoutManager.VERTICAL, false));
    }

    /**
     * Comparing dates to determine whether to show the reminder button or not
     * @param movie Movie object
     * @param reminderButton button to remind the user when movie is released.
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void compareDates(Movie movie, Button reminderButton) {
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
    }

    /**
     * Write comments to the Firebase.
     * @param database Firebase database
     * @param movie Movie object
     * @param commentBox EditText where the comment is taken from
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void writeToDatabase(FirebaseDatabase database, Movie movie,
                                       EditText commentBox) {
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
    }

    /**
     * Read the comments from the Firebase
     * @param commentAdapter Adapter for comments
     * @param commentRef Reference to comments
     */
    public static void readFromTheDatabase(final CommentAdapter commentAdapter,
                                           DatabaseReference commentRef) {
        commentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
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
            }
        });
    }
}
