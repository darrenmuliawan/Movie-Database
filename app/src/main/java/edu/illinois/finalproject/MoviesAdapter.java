package edu.illinois.finalproject;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by darrenalexander on 12/5/17.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {
    public static final String MOVIE = "Movie";
    public static final String EMPTY_STRING = "";
    private List<Movie> listOfMovies;

    /**
     * Constructor for RestaurantAdapter
     * @param movies
     */
    public MoviesAdapter(List<Movie> movies) {
        listOfMovies = movies;
    }

    /**
     * Add restaurants to the restaurant list
     * @param movie
     */
    public void addMovies(Movie movie) {
        listOfMovies.add(movie);
    }

    @Override
    public int getItemViewType(int position) {
        Movie movie = listOfMovies.get(position);
        return R.layout.;
        //return (!restaurant.getImageUrl().equals(EMPTY_STRING)) ?
        //        R.layout.restaurant_list_with_images : R.layout.restaurant_list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View movieItem = LayoutInflater.from(parent.getContext()).
                inflate(viewType, parent, false);
        return new ViewHolder(movieItem);
    }

    @Override
    public void onBindViewHolder(MoviesAdapter.ViewHolder holder, int position) {
        final Movie movie = listOfMovies.get(position);


        holder.movieName.setText(movie.getTitle());
        holder.movieRating.setText(movie.getRating());
        final Context context = holder.itemView.getContext();
        Picasso.with(context).load(movie.getImageUrl()).into(holder.movieImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Context context = view.getContext();
                Intent detailedIntent = new Intent(context, DetailActivity.class);
                detailedIntent.putExtra(MOVIE, movie);
                context.startActivity(detailedIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listOfMovies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View itemView;
        public TextView movieName;
        public TextView movieRating;
        public ImageView movieImage;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            this.movieName = (TextView) itemView.findViewById(R.id.);
            this.movieRating = (TextView) itemView.findViewById(R.id.);
            this.movieImage = (ImageView) itemView.findViewById(R.id.);
        }
    }
}
