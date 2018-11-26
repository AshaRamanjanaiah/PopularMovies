package com.example.android.popularmoviesapp.service;

import android.util.Log;

import com.example.android.popularmoviesapp.DetailActivity;
import com.example.android.popularmoviesapp.NetworkUtils.Constant;
import com.example.android.popularmoviesapp.OnTrailerDataChanged;
import com.example.android.popularmoviesapp.model.ReviewsDataList;
import com.example.android.popularmoviesapp.model.TrailerData;
import com.example.android.popularmoviesapp.model.TrailerDataList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TrailerDataController implements Callback<TrailerDataList> {

    private static final String TAG = TrailerDataController.class.getSimpleName();

    private OnTrailerDataChanged trailerDataChangeListener;

    public TrailerDataController(OnTrailerDataChanged onTrailerDataChanged){
        trailerDataChangeListener = onTrailerDataChanged;
    }

    @Override
    public void onResponse(Call<TrailerDataList> call, Response<TrailerDataList> response) {
        if(response.isSuccessful()){

            Log.d(TAG, response.body().toString());

            TrailerDataList trailerDataList = response.body();

            trailerDataChangeListener.OnTrailerDataChanged(trailerDataList.getTrailersData());

            Log.d(TAG, "Successfully got Trailers data");
        }else{
            Log.d(TAG, "Failed to get response");
            Log.d(TAG, String.valueOf(response.errorBody()));
        }
    }

    @Override
    public void onFailure(Call<TrailerDataList> call, Throwable t) {
        Log.d(TAG, String.valueOf(t.getStackTrace()));
    }
}
