package com.example.android.popularmoviesapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewsDataList {

    @SerializedName("results")
    private List<ReviewsData> reviewsDataList;

    public List<ReviewsData> getReviewsDataList() {
        return reviewsDataList;
    }

    public void setReviewsDataList(List<ReviewsData> reviewsDataList) {
        this.reviewsDataList = reviewsDataList;
    }
}
