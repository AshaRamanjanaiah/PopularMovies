package com.example.android.popularmoviesapp.NetworkUtils;

import com.example.android.popularmoviesapp.model.MovieData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public final class MoviesInfoJsonUtils {

    public static List<MovieData> parseMovieDataJson(String movieJsonData) throws JSONException {

        final String API_RESULTS = "results";
        final String IMAGE_THUMNAIL = "poster_path";
        final String TITLE = "title";
        final String OVERVIEW = "overview";
        final String VOTE_AVERAGE = "vote_average";
        final String RELEASE_DATE = "release_date";

        String imageThumbnail = "";
        String movieTitle = "";
        String movieOverview = "";
        String movieVoteAverage = "";
        String movieReleaseDate = "";

        JSONObject jsonObject = new JSONObject(movieJsonData);

        JSONArray moviesArray = jsonObject.optJSONArray(API_RESULTS);
        List<MovieData> movieData = new ArrayList<>();

        for( int i=0; i<= moviesArray.length(); i++ ){
            JSONObject resultsObject = moviesArray.optJSONObject(i);
            if(resultsObject != null) {
                imageThumbnail = resultsObject.optString(IMAGE_THUMNAIL);
                movieTitle = resultsObject.optString(TITLE);
                movieOverview = resultsObject.optString(OVERVIEW);
                movieVoteAverage = resultsObject.optString(VOTE_AVERAGE);
                movieReleaseDate = resultsObject.optString(RELEASE_DATE);
            }
            movieData.add(new MovieData(movieTitle, movieVoteAverage, movieReleaseDate, movieOverview, imageThumbnail));
        }




        return movieData;
    }

}
