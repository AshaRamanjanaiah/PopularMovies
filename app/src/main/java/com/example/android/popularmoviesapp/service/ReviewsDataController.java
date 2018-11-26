package com.example.android.popularmoviesapp.service;

import android.util.Log;

import com.example.android.popularmoviesapp.OnReviewsDataChanged;
import com.example.android.popularmoviesapp.model.ReviewsDataList;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewsDataController implements Callback<ReviewsDataList> {

    private static final String TAG = ReviewsDataController.class.getSimpleName();

    private OnReviewsDataChanged reviewsDataChangeListener;

    public ReviewsDataController(OnReviewsDataChanged onReviewsDataChanged){
        this.reviewsDataChangeListener = onReviewsDataChanged;
    }


    @Override
    public void onResponse(Call<ReviewsDataList> call, Response<ReviewsDataList> response) {
        if(response.isSuccessful()){

            Log.d(TAG, response.body().toString());

            ReviewsDataList reviewsDataList = response.body();

            reviewsDataChangeListener.OnReviewDataChanged(reviewsDataList.getReviewsDataList());

            Log.d(TAG, "Successfully got Reviews data");
        }else{
            Log.d(TAG, "Failed to get response");
            Log.d(TAG, String.valueOf(response.errorBody()));
        }
    }

    @Override
    public void onFailure(Call<ReviewsDataList> call, Throwable t) {

    }
}
