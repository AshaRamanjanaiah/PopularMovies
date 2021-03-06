package com.example.android.popularmoviesapp;

/**
 * Created by Asha Ramanjanaiah on 10/18/2018
 */

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.ConnectivityManager;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;


import com.example.android.popularmoviesapp.NetworkUtils.Constant;
import com.example.android.popularmoviesapp.database.FavoritesMovieDataDB;
import com.example.android.popularmoviesapp.model.FetchMoviesData;
import com.example.android.popularmoviesapp.model.MovieData;
import com.example.android.popularmoviesapp.model.OnTaskCompleted;

import java.util.ArrayList;
import java.util.List;

public class MainActivityFragment extends Fragment implements OnTaskCompleted {

    private static final String TAG = MainActivityFragment.class.getSimpleName();

    private ImageAdapter imageAdapter;

    private TextView mEmptyFavoriteListTextView;

    private ArrayList<MovieData> movieDataArrayList = new ArrayList<>();

    private String sortBy = Constant.SORTBY_BASE;

    private GridView mGridView;

    private int mCurrentPosition = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if(bundle != null){
            sortBy = bundle.getString(Constant.SORTBY);
        }

        if(savedInstanceState != null && savedInstanceState.containsKey(Constant.SORTBY)){
            sortBy = savedInstanceState.getString(Constant.SORTBY);
            if(sortBy.equals(Constant.FAVORITES)){
                fetchLatestDataFromDB();
                return;
            }
        }

        if(savedInstanceState == null || !savedInstanceState.containsKey(Constant.MOVIE_INFO)){
            if(sortBy.equals(Constant.FAVORITES)){
                fetchLatestDataFromDB();
            }else {
                fetchDataOverNetwork(sortBy);
            }
        } else {
            Log.v(TAG, "onSavedInstanceState has data");
            movieDataArrayList = savedInstanceState.getParcelableArrayList(Constant.MOVIE_INFO);
            if(savedInstanceState.containsKey(Constant.POSITION)){
                mCurrentPosition = savedInstanceState.getInt(Constant.POSITION);
            }
        }

    }

    public void fetchDataOverNetwork(String sortBy){
        Log.v(TAG, "Fetching data from network");
        FetchMoviesData fetchMoviesData = new FetchMoviesData(this, isNetAvailable());
        fetchMoviesData.execute(sortBy);
    }

    public void fetchLatestDataFromDB(){
        final MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.getMovieInfo().observe(this, new Observer<List<FavoritesMovieDataDB>>() {
            @Override
            public void onChanged(@Nullable List<FavoritesMovieDataDB> favoritesMovieDataDBS) {
                if(favoritesMovieDataDBS.size() == 0){
                    Log.d(TAG, "Favorites List is empty");
                    mEmptyFavoriteListTextView.setVisibility(View.VISIBLE);
                }
                movieDataArrayList.clear();
                Log.d(TAG, "Updating movies from LiveData in viewModel");
                for (FavoritesMovieDataDB movieData : favoritesMovieDataDBS) {
                    movieDataArrayList.add(new MovieData(movieData.getTitle(), movieData.getMovieVoteAverage(),
                            movieData.getMovieReleaseDate(), movieData.getMovieOverview(), movieData.getImageThumbnail(), movieData.getMovieId())); }
                    imageAdapter.notifyDataSetChanged();
                    sortBy = Constant.FAVORITES;
                    }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(Constant.MOVIE_INFO, movieDataArrayList);
        outState.putString(Constant.SORTBY, sortBy);
        outState.putInt(Constant.POSITION, mGridView.getFirstVisiblePosition());
        super.onSaveInstanceState(outState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_layout, container, false);

        imageAdapter = new ImageAdapter(getActivity(), 0, movieDataArrayList);
        mEmptyFavoriteListTextView = (TextView) rootView.findViewById(R.id.tv_empty_favorite_list);

        mGridView = (GridView) rootView.findViewById(R.id.gv_movieThumbnl);
        mGridView.setSelection(mCurrentPosition);
        mGridView.setAdapter(imageAdapter);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MovieData movieData = imageAdapter.getItem(position);
                launchActivity(movieData);
            }
        });

        return rootView;
    }

    @Override
    public void onTaskCompleted(List<MovieData> movieDataList) {

        if(movieDataList == null || movieDataList.size() == 0){
            Log.v(TAG, "Movie data list is empty");
            return;
        }

        for (MovieData movieData : movieDataList) {
            movieDataArrayList.add(new MovieData(movieData.getTitle(), movieData.getMovieVoteAverage(),
                    movieData.getMovieReleaseDate(), movieData.getMovieOverview(),movieData.getImageThumbnail(), movieData.getMovieId()));
        }
        imageAdapter.notifyDataSetChanged();

    }

    private void launchActivity(MovieData movieData){
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("movieData", movieData);
        startActivity(intent);
    }

    private Boolean isNetAvailable() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
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
