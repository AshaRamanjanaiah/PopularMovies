package com.example.android.popularmoviesapp.NetworkUtils;

import com.example.android.popularmoviesapp.BuildConfig;

public class Constant {
    public static final String MOVIEDB_URL =
            "http://api.themoviedb.org/3/discover/movie";

    public static final String MOVIEDB_ONPOPULARITY_URL =
            "https://api.themoviedb.org/3/movie/popular";

    public static final String MOVIEDB_ONHIGHEST_RATING_URL =
            "https://api.themoviedb.org/3/movie/top_rated";

    public static final String API_KEY = "api_key";

    // Use your own API key
    public static final String api_value = BuildConfig.API_KEY;

    public static final String MOST_POPULAR = "mostPopular";

    public static final String TOP_RATED = "topRated";

    public final static String BASE_URL = "http://image.tmdb.org/t/p/w185";

    public final static String MOVIE_DATA = "movieData";

    public final static String SORTBY = "sortBy";

    public final static String SORTBY_BASE = "base";

    public final static String SORYBY_POPULARITY = "mostPopular";

    public final static String SORYBY_HIGH_RATED = "topRated";

    public final static String MOVIE_INFO = "Moviesinfo";

    public final static String FAVORITES = "favorites";
}
