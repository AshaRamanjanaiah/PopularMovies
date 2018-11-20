package com.example.android.popularmoviesapp.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface FavoritesMoviesDao {

    @Query("SELECT * FROM movieData ORDER BY movieVoteAverage")
    LiveData<List<FavoritesMovieDataDB>> loadAllMovies();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(FavoritesMovieDataDB favoritesMovieData);

    @Query("DELETE FROM movieData WHERE title =  :title")
    void deleteMovie(String title);

    @Query("SELECT * FROM movieData WHERE title =  :title")
    LiveData<FavoritesMovieDataDB> ifMarkedAsFavorite(String title);

}
