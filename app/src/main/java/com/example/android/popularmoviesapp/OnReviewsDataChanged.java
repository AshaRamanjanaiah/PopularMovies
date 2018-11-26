package com.example.android.popularmoviesapp;

import com.example.android.popularmoviesapp.model.ReviewsData;

import java.util.List;

public interface OnReviewsDataChanged {
    void OnReviewDataChanged(List<ReviewsData> reviewsData);
}
