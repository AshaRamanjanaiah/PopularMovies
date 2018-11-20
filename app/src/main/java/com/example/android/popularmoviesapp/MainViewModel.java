package com.example.android.popularmoviesapp;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.android.popularmoviesapp.database.AppDatabase;
import com.example.android.popularmoviesapp.database.FavoritesMovieDataDB;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private static final String TAG = MainViewModel.class.getSimpleName();

    LiveData<List<FavoritesMovieDataDB>> movieInfo;
    public MainViewModel(@NonNull Application application) {
        super(application);

        AppDatabase appDatabase = AppDatabase.getsInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving data from Livedata to viewModel");
        movieInfo = appDatabase.favoritesMoviesDao().loadAllMovies();
    }

    public LiveData<List<FavoritesMovieDataDB>> getMovieInfo() {
        return movieInfo;
    }
}
