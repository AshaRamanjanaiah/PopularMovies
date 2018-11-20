package com.example.android.popularmoviesapp.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "movieData")
public class FavoritesMovieDataDB {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String movieReleaseDate;
    private String movieVoteAverage;
    private String movieOverview;
    private String imageThumbnail;
    private boolean markedAsFavorite;

    @Ignore
    public FavoritesMovieDataDB(String title, String movieReleaseDate, String movieVoteAverage,
                                String movieOverview, String imageThumbnail, boolean markedAsFavorite){
        this.title = title;
        this.movieVoteAverage = movieVoteAverage;
        this.movieReleaseDate = movieReleaseDate;
        this.movieOverview = movieOverview;
        this.imageThumbnail = imageThumbnail;
        this.markedAsFavorite = markedAsFavorite;
    }

    public FavoritesMovieDataDB(int id, String title, String movieVoteAverage, String movieReleaseDate,
                                String movieOverview, String imageThumbnail, boolean markedAsFavorite){
        this.id = id;
        this.title = title;
        this.movieVoteAverage = movieVoteAverage;
        this.movieReleaseDate = movieReleaseDate;
        this.movieOverview = movieOverview;
        this.imageThumbnail = imageThumbnail;
        this.markedAsFavorite = markedAsFavorite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageThumbnail() {
        return imageThumbnail;
    }

    public void setImageThumbnail(String imageThumbnail) {
        this.imageThumbnail = imageThumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMovieVoteAverage() {
        return movieVoteAverage;
    }

    public void setMovieVoteAverage(String movieVoteAverage) {
        this.movieVoteAverage = movieVoteAverage;
    }

    public String getMovieReleaseDate() {
        return movieReleaseDate;
    }

    public void setMovieReleaseDate(String movieReleaseDate) {
        this.movieReleaseDate = movieReleaseDate;
    }

    public String getMovieOverview() {
        return movieOverview;
    }

    public void setMovieOverview(String movieOverview) {
        this.movieOverview = movieOverview;
    }

    public boolean isMarkedAsFavorite() {
        return markedAsFavorite;
    }

    public void setMarkedAsFavorite(boolean markedAsFavorite) {
        this.markedAsFavorite = markedAsFavorite;
    }
}
