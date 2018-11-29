package com.example.android.popularmoviesapp.NetworkUtils;

/**
 * Created by Asha Ramanjanaiah on 19/18/2018
 */

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public final class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static String MOVIEDB_BASE_URL = Constant.MOVIEDB_URL;

    private NetworkUtils(){ }

    public static URL buildURL(String sortBy){
        MOVIEDB_BASE_URL = getMoviedbBaseUrl(sortBy);
        Uri buildUri = Uri.parse(MOVIEDB_BASE_URL).buildUpon()
                .appendQueryParameter(Constant.API_KEY, Constant.api_value)
                .build();
        URL url = null;

        try {
            url = new URL(buildUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e(TAG, "Error building URL");
        }

        Log.v(TAG, "Built URL");

        return url;
    }


    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        try {
            InputStream inputStream = httpURLConnection.getInputStream();

            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");

            boolean hasNext = scanner.hasNext();

            if(hasNext){
                return scanner.next();
            }else {
                return null;
            }

        } finally {
            httpURLConnection.disconnect();
        }


    }

    private static String getMoviedbBaseUrl(String sortBy){
        MOVIEDB_BASE_URL = Constant.MOVIEDB_URL;
        if(sortBy != null && !sortBy.isEmpty()){
            if(sortBy.equals(Constant.MOST_POPULAR)){
                MOVIEDB_BASE_URL = Constant.MOVIEDB_ONPOPULARITY_URL;
            }else if(sortBy.equals(Constant.TOP_RATED)){
                MOVIEDB_BASE_URL = Constant.MOVIEDB_ONHIGHEST_RATING_URL;
            }
        }
        return MOVIEDB_BASE_URL;
    }

}
