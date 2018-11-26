package com.example.android.popularmoviesapp.service;

import com.example.android.popularmoviesapp.model.ReviewsDataList;
import com.example.android.popularmoviesapp.model.TrailerData;
import com.example.android.popularmoviesapp.model.TrailerDataList;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MoviesAPI {

    @GET("/3/movie/{id}/videos")
    Call<TrailerDataList> getTrailers(@Path("id") int movieId, @Query("api_key") String apiKey);

    @GET("/3/movie/{id}/reviews")
    Call<ReviewsDataList> getReviews(@Path("id") int movieId, @Query("api_key") String apiKey);

}
