package com.example.android.popularmoviesapp.service;

import com.example.android.popularmoviesapp.model.TrailerData;
import com.example.android.popularmoviesapp.model.TrailerDataList;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MoviesAPI {

    @GET("/3/movie/335983/videos")
    Call<TrailerDataList> getTrailers(@Query("api_key") String apiKey);
}
