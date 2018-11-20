package com.example.android.popularmoviesapp.model;

/**
 * Created by Asha Ramanjanaiah on 19/18/2018
 */

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.util.Log;

import com.example.android.popularmoviesapp.NetworkUtils.MoviesInfoJsonUtils;
import com.example.android.popularmoviesapp.NetworkUtils.NetworkUtils;

import java.net.URL;
import java.util.List;

public class FetchMoviesData extends AsyncTask<String, Void, List<MovieData>> {

    private final static String TAG = FetchMoviesData.class.getSimpleName();

    private final OnTaskCompleted taskCompleted;

    private final Context context;

    public FetchMoviesData(OnTaskCompleted taskCompleted, Context context){
        this.taskCompleted = taskCompleted;
        this.context = context;
    }

    @Override
    protected List<MovieData> doInBackground(String... strings) {

        if(strings.length == 0){
            return null;
        }

        String sortBy = strings[0];

        URL popularMovieRequestUrl = NetworkUtils.buildURL(sortBy);
        if(isNetAvailable(context)){

            try {
                String movieResponse = NetworkUtils.getResponseFromHttpUrl(popularMovieRequestUrl);

                return MoviesInfoJsonUtils.parseMovieDataJson(movieResponse);

            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "Network error");
            }

        }

        return null;
    }

    @Override
    protected void onPostExecute(List<MovieData> movieData) {
        if(movieData != null && movieData.size() != 0) {
            taskCompleted.onTaskCompleted(movieData);
        } else {
            taskCompleted.onTaskCompleted(null);
        }
    }

    private Boolean isNetAvailable(Context con) {

        ConnectivityManager connectivityManager = (ConnectivityManager) con.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager!= null && connectivityManager.getActiveNetworkInfo() != null
                    && connectivityManager.getActiveNetworkInfo().isAvailable()
                    && connectivityManager.getActiveNetworkInfo().isConnected()) {
                Log.v(TAG, "Internet Connection Present");
                return true;
            } else {
                Log.v(TAG, "Internet Connection Not Present");
                return false;
            }

    }

}
