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

    @SerializedName("backdrop_path")
    private String detailImageUrl;

    @SerializedName("original_language")
    private String language;

    @SerializedName("genre_ids")
    private int[] genre;
    private boolean adult;
    private String overview;

    @SerializedName("release_date")
    private String releaseDate;



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

    public String getDetailImageUrl() {
        return detailImageUrl;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.voteCount);
        dest.writeDouble(this.rating);
        dest.writeString(this.title);
        dest.writeString(this.imageUrl);
        dest.writeString(this.detailImageUrl);
        dest.writeString(this.language);
        dest.writeIntArray(this.genre);
        dest.writeByte(this.adult ? (byte) 1 : (byte) 0);
        dest.writeString(this.overview);
        dest.writeString(this.releaseDate);
    }

    public Movie() {
    }

    protected Movie(Parcel in) {
        this.voteCount = in.readInt();
        this.rating = in.readDouble();
        this.title = in.readString();
        this.imageUrl = in.readString();
        this.detailImageUrl = in.readString();
        this.language = in.readString();
        this.genre = in.createIntArray();
        this.adult = in.readByte() != 0;
        this.overview = in.readString();
        this.releaseDate = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
