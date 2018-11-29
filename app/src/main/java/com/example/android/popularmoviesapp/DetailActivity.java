package com.example.android.popularmoviesapp;

/**
 * Created by Asha Ramanjanaiah on 15/11/2018
 */

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmoviesapp.NetworkUtils.AppExecutor;
import com.example.android.popularmoviesapp.NetworkUtils.Constant;
import com.example.android.popularmoviesapp.database.AppDatabase;
import com.example.android.popularmoviesapp.database.FavoritesMovieDataDB;
import com.example.android.popularmoviesapp.model.MovieData;
import com.example.android.popularmoviesapp.model.ReviewsData;
import com.example.android.popularmoviesapp.model.TrailerData;
import com.example.android.popularmoviesapp.service.ReviewsDataController;
import com.example.android.popularmoviesapp.service.TrailerDataController;
import com.example.android.popularmoviesapp.service.MoviesAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener, OnTrailerDataChanged, OnReviewsDataChanged, TrailerAdapter.ListItemClickListener{

    private static final String TAG = DetailActivity.class.getSimpleName();
    private static final String MARK_AS_FAVORITE = "MarkAsFavorite";

    private static final String BASE_URL = "http://api.themoviedb.org/";

    private static final String YOUTUBE_VIDEO_URL = "http://www.youtube.com/watch?v=";

    private TextView mTitleTextview;
    private ImageView mThumbnailImageview;
    private TextView mReleaseDateTextview;
    private TextView mVoteAverageTextview;
    private TextView mReviewTextview;
    private Button mMarkAsFavoriteButton;

    private TextView mNoReviewsTextview;
    private TextView mShareTrailerTextview;

    private String mMovieTitle;
    private String mMovieReleaseDate;
    private String mMovieVoteAverage;
    private String mMovieOverview;
    private String mImageThumbnail;
    private int mMovieId;

    MoviesAPI moviesAPI;

    private AppDatabase appDatabase;

    private boolean mMarkAsFavorite = false;
    private FavoritesMovieDataDB mMovieData = null;

    private List<TrailerData> mTrailerData = new ArrayList<>();

    private List<ReviewsData> mReviewsData = new ArrayList<>();

    private String mFirstTrailerKey = "";

    private TrailerAdapter mTrailerAdapter;
    private RecyclerView mTrailerRecyclerView;

    private ReviewAdapter mReviewAdapter;
    private RecyclerView mReviewRecycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        appDatabase = AppDatabase.getsInstance(getApplicationContext());

        mTitleTextview = (TextView) findViewById(R.id.tv_title);
        mThumbnailImageview = (ImageView) findViewById(R.id.iv_movie_thumbnail);
        mReleaseDateTextview = (TextView) findViewById(R.id.tv_releaseDate);
        mVoteAverageTextview = (TextView) findViewById(R.id.tv_voteAverage);
        mReviewTextview = (TextView) findViewById(R.id.tv_review);
        mMarkAsFavoriteButton = (Button) findViewById(R.id.bt_markAsFavorite);
        mMarkAsFavoriteButton.setOnClickListener(this);

        mTrailerRecyclerView = (RecyclerView) findViewById(R.id.rv_trailers);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mTrailerRecyclerView.setLayoutManager(layoutManager);
        mTrailerRecyclerView.setHasFixedSize(true);
        mTrailerRecyclerView.setNestedScrollingEnabled(false);
        mTrailerAdapter = new TrailerAdapter(mTrailerData, this);
        mTrailerRecyclerView.setAdapter(mTrailerAdapter);

        mReviewRecycleView = (RecyclerView) findViewById(R.id.rv_reviews);

        LinearLayoutManager linearLayoutManagerReview = new LinearLayoutManager(this);
        linearLayoutManagerReview.setOrientation(LinearLayout.VERTICAL);
        mReviewRecycleView.setLayoutManager(linearLayoutManagerReview);
        mReviewRecycleView.setHasFixedSize(true);
        mReviewRecycleView.setNestedScrollingEnabled(false);
        mReviewAdapter = new ReviewAdapter(mReviewsData);
        mReviewRecycleView.setAdapter(mReviewAdapter);

        mNoReviewsTextview = (TextView) findViewById(R.id.tv_no_reviews);
        mShareTrailerTextview = (TextView) findViewById(R.id.tv_share_trailer);
        mShareTrailerTextview.setOnClickListener(this);


        Intent intent = getIntent();
        if(intent.hasExtra(Constant.MOVIE_DATA)){
            MovieData movieData = intent.getParcelableExtra(Constant.MOVIE_DATA);
            populateDatainUI(movieData);
        }
        if(savedInstanceState != null && savedInstanceState.containsKey(MARK_AS_FAVORITE)){
            mMarkAsFavorite = savedInstanceState.getBoolean(MARK_AS_FAVORITE);
            showFavoriteButton(mMarkAsFavorite);
        }else {
            checkIfMarkedAsFavoriteInDB();
        }

        createMovieAPI();
        moviesAPI.getTrailers(mMovieId, Constant.api_value).enqueue(new TrailerDataController(this));
        moviesAPI.getReviews(mMovieId, Constant.api_value).enqueue(new ReviewsDataController(this));
    }

    private void populateDatainUI(MovieData movieData){

        if(movieData.getTitle() != null && !movieData.getTitle().equals("")) {
            mMovieTitle = movieData.getTitle();
            mTitleTextview.setText(mMovieTitle);
        }

        if(movieData.getMovieReleaseDate() != null && !movieData.getMovieReleaseDate().equals("")) {
            mMovieReleaseDate = movieData.getMovieReleaseDate();
            mReleaseDateTextview.setText(mMovieReleaseDate);
        }

        if(movieData.getMovieVoteAverage() != null && !movieData.getMovieVoteAverage().equals("")) {
            mMovieVoteAverage = movieData.getMovieVoteAverage()+"/10";
            mVoteAverageTextview.setText(mMovieVoteAverage);
        }

        if(movieData.getMovieOverview() != null && !movieData.getMovieOverview().equals("")) {
            mMovieOverview = movieData.getMovieOverview();
            mReviewTextview.setText(mMovieOverview);
        }

        if(movieData.getMovieId() != 0) {
            mMovieId = movieData.getMovieId();
        }

        if(movieData.getImageThumbnail() != null && !movieData.getImageThumbnail().equals("")) {
            mImageThumbnail = movieData.getImageThumbnail();
            Picasso.with(DetailActivity.this)
                    .load(Constant.BASE_URL + mImageThumbnail)
                    .error(R.drawable.sample_5)
                    .placeholder(R.drawable.sample_5)
                    .into(mThumbnailImageview);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.bt_markAsFavorite:
                if(!mMarkAsFavorite){
                    mMarkAsFavorite = true;
                    addMovieToDB();
                }else {
                    mMarkAsFavorite = false;
                    removeMovieFromDB();
                }
                break;
            case R.id.tv_share_trailer:
                shareTrailerURL();
                break;
                default:
                    Toast.makeText(this, "Default", Toast.LENGTH_LONG).show();
        }
    }

    private void addMovieToDB(){
        mMovieData = new FavoritesMovieDataDB(mMovieTitle,
                mMovieReleaseDate, mMovieVoteAverage, mMovieOverview, mImageThumbnail, mMovieId, true);
        AppExecutor.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                appDatabase.favoritesMoviesDao().insertMovie(mMovieData);
            }
        });
        Toast.makeText(this, "Movie added to Favorites DB", Toast.LENGTH_LONG).show();
        Log.d(TAG, "Movie added to Favorites DB");
        mMarkAsFavoriteButton.setText(getResources().getString(R.string.unmark_as_favorite));
    }

    private void removeMovieFromDB(){
        AppExecutor.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                appDatabase.favoritesMoviesDao().deleteMovie(mMovieTitle);
            }
        });

        Toast.makeText(this, "Movie removed from Favorites DB", Toast.LENGTH_LONG).show();
        Log.d(TAG, "Movie removed from Favorites DB");
        mMarkAsFavoriteButton.setText(getResources().getString(R.string.mark_as_favorite));
    }

    public void checkIfMarkedAsFavoriteInDB(){
        LiveData<FavoritesMovieDataDB> favoritesMovieData = appDatabase.favoritesMoviesDao().ifMarkedAsFavorite(mMovieTitle);
        favoritesMovieData.observe(this, new Observer<FavoritesMovieDataDB>() {
            @Override
            public void onChanged(@Nullable FavoritesMovieDataDB favoritesMovieData) {
                Log.d(TAG, "OnChange method is triggered");
                mMovieData = favoritesMovieData;

                if(mMovieData!= null) {
                    mMarkAsFavorite = favoritesMovieData.isMarkedAsFavorite();
                }
                showFavoriteButton(mMarkAsFavorite);
            }
        });
    }

    public void showFavoriteButton(boolean mMarkAsFavorite){

        if(mMarkAsFavorite){
            mMarkAsFavoriteButton.setText(getResources().getString(R.string.unmark_as_favorite));
        }else {
            mMarkAsFavoriteButton.setText(getResources().getString(R.string.mark_as_favorite));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(MARK_AS_FAVORITE, mMarkAsFavorite);
        super.onSaveInstanceState(outState);
    }

    public void shareTrailerURL(){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, YOUTUBE_VIDEO_URL+mFirstTrailerKey);
        sendIntent.setType("text/plain");
        if(sendIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.share_trailer)));
        }
    }

    @Override
    public void OnTrailerDataChanged(List<TrailerData> trailerData) {
        if(trailerData != null && trailerData.size() != 0){
            mTrailerData.addAll(trailerData);
            mFirstTrailerKey = trailerData.get(0).getKey();
            mTrailerRecyclerView.setAdapter(new TrailerAdapter(mTrailerData, this));
        }
    }

    public void createMovieAPI(){
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            moviesAPI = retrofit.create(MoviesAPI.class);
    }

    @Override
    public void OnReviewDataChanged(List<ReviewsData> reviewsData) {
        if(reviewsData != null && reviewsData.size() != 0){
            mReviewsData.addAll(reviewsData);
            mReviewRecycleView.setAdapter(new ReviewAdapter(mReviewsData));
        }else {
            mNoReviewsTextview.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onListItemClick(String clickedItemKey) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(YOUTUBE_VIDEO_URL + clickedItemKey));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
