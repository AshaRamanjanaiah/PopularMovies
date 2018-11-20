package com.example.android.popularmoviesapp.model;

import android.arch.persistence.room.Entity;
import android.os.Parcel;
import android.os.Parcelable;

public class MovieData implements Parcelable {

    private String imageThumbnail;
    private String title;
    private String movieVoteAverage;
    private String movieReleaseDate;
    private String movieOverview;

    public MovieData(String title, String movieVoteAverage, String movieReleaseDate, String movieOverview, String imageThumbnail){
        this.title = title;
        this.movieVoteAverage = movieVoteAverage;
        this.movieReleaseDate = movieReleaseDate;
        this.movieOverview = movieOverview;
        this.imageThumbnail = imageThumbnail;
    }

    private MovieData(Parcel in){
        title = in.readString();
        movieVoteAverage = in.readString();
        movieReleaseDate = in.readString();
        movieOverview = in.readString();
        imageThumbnail = in.readString();
    }

    public String getImageThumbnail() {
        return imageThumbnail;
    }

    public String getTitle(){ return title;}

    public String getMovieVoteAverage() {
        return movieVoteAverage;
    }

    public String getMovieReleaseDate() {
        return movieReleaseDate;
    }

    public String getMovieOverview() {
        return movieOverview;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(movieVoteAverage);
        dest.writeString(movieReleaseDate);
        dest.writeString(movieOverview);
        dest.writeString(imageThumbnail);
    }

    public static final Parcelable.Creator<MovieData> CREATOR = new Creator<MovieData>() {

        @Override
        public MovieData createFromParcel(Parcel source) {
            return new MovieData(source);
        }

        @Override
        public MovieData[] newArray(int size) {
            return new MovieData[size];
        }
    };
}
