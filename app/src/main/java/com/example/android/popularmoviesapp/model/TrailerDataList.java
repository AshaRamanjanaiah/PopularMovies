package com.example.android.popularmoviesapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TrailerDataList {

    @SerializedName("results")
    private List<TrailerData> trailersData;

    public List<TrailerData> getTrailersData() {
        return trailersData;
    }

    public void setTrailersData(List<TrailerData> trailersData) {
        this.trailersData = trailersData;
    }
}
