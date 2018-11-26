package com.example.android.popularmoviesapp.service;

import android.util.Log;

import com.example.android.popularmoviesapp.DetailActivity;
import com.example.android.popularmoviesapp.NetworkUtils.Constant;
import com.example.android.popularmoviesapp.OnTrailerDataChanged;
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

public class DataController implements Callback<TrailerDataList> {

    private static final String TAG = DataController.class.getSimpleName();

    private static final String BASE_URL = "http://api.themoviedb.org/";

    private OnTrailerDataChanged trailerDataChangeListener;

    public DataController(OnTrailerDataChanged onTrailerDataChanged){
        trailerDataChangeListener = onTrailerDataChanged;
    }


    public void start(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        MoviesAPI moviesAPI = retrofit.create(MoviesAPI.class);

        Call<TrailerDataList> call = moviesAPI.getTrailers(Constant.api_value);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<TrailerDataList> call, Response<TrailerDataList> response) {
        if(response.isSuccessful()){
            //   List<TrailerData> trailerDataList = response.body();
            //  TrailerData data = trailerDataList.get(0);
            // String name = data.getTrailerTitle();

            Log.d(TAG, response.body().toString());

            TrailerDataList trailerDataList = response.body();

            trailerDataChangeListener.OnTrailerDataChanged(trailerDataList.getTrailersData());

            Log.d(TAG, "response successful");
        }else{
            Log.d(TAG, "response failed");
            Log.d(TAG, String.valueOf(response.errorBody()));
        }
    }

    @Override
    public void onFailure(Call<TrailerDataList> call, Throwable t) {
        Log.d(TAG, String.valueOf(t.getStackTrace()));
    }
}
