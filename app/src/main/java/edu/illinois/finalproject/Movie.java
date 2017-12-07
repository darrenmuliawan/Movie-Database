package edu.illinois.finalproject;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by darrenalexander on 12/5/17.
 */

public class Movie implements Parcelable {

    @SerializedName("vote_count")
    private int voteCount;

    @SerializedName("vote_average")
    private double rating;
    private String title;

    @SerializedName("poster_path")
    private String imageUrl;

    @SerializedName("original_language")
    private String language;

    @SerializedName("genre_ids")
    private int[] genre;
    private boolean adult;
    private String overview;

    @SerializedName("release_date")
    private String releaseDate;

    protected Movie(Parcel in) {
        voteCount = in.readInt();
        rating = in.readInt();
        title = in.readString();
        imageUrl = in.readString();
        language = in.readString();
        genre = in.createIntArray();
        adult = in.readByte() != 0;
        overview = in.readString();
        releaseDate = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(voteCount);
        dest.writeInt((int) rating);
        dest.writeString(title);
        dest.writeString(imageUrl);
        dest.writeString(language);
        dest.writeIntArray(genre);
        dest.writeByte((byte) (adult ? 1 : 0));
        dest.writeString(overview);
        dest.writeString(releaseDate);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public int getVoteCount() {
        return voteCount;
    }

    public double getRating() {
        return rating;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getLanguage() {
        return language;
    }

    public int[] getGenre() {
        return genre;
    }

    public boolean isAdult() {
        return adult;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }
}
