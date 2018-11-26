package com.example.android.popularmoviesapp;

import com.example.android.popularmoviesapp.model.TrailerData;

import java.util.List;

public interface OnTrailerDataChanged {
    void OnTrailerDataChanged(List<TrailerData> trailerData);
}
