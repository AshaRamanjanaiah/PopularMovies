package com.example.android.popularmoviesapp.model;

import com.google.gson.annotations.SerializedName;

public class TrailerData {

    @SerializedName("name")
    private String name;

    @SerializedName("key")
    private String key;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}

