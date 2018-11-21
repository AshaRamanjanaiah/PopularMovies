package com.example.android.popularmoviesapp;

/**
 * Created by Asha Ramanjanaiah on 15/11/2018
 */

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
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
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = DetailActivity.class.getSimpleName();
    private static final String MARK_AS_FAVORITE = "MarkAsFavorite";

    private TextView mTitleTextview;
    private ImageView mThumbnailImageview;
    private TextView mReleaseDateTextview;
    private TextView mVoteAverageTextview;
    private TextView mReviewTextview;
    private Button mMarkAsFavoriteButton;

    private String mMovieTitle;
    private String mMovieReleaseDate;
    private String mMovieVoteAverage;
    private String mMovieOverview;
    private String mImageThumbnail;

    private AppDatabase appDatabase;

    private boolean mMarkAsFavorite = false;
    private FavoritesMovieDataDB mMovieData = null;

    private static int LIST_OF_TRAILERS = 3;
    private TrailerAdapter mTrailerAdapter;
    private RecyclerView mTrailerRecyclerView;

    private static int LIST_OF_REVIEWS = 3;
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

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayout.VERTICAL);
        mTrailerRecyclerView.setLayoutManager(linearLayoutManager);
        mTrailerRecyclerView.setHasFixedSize(true);
        mTrailerRecyclerView.setNestedScrollingEnabled(false);
        mTrailerAdapter = new TrailerAdapter(LIST_OF_TRAILERS);
        mTrailerRecyclerView.setAdapter(mTrailerAdapter);

        mReviewRecycleView = (RecyclerView) findViewById(R.id.rv_reviews);

        LinearLayoutManager linearLayoutManagerReview = new LinearLayoutManager(this);
        linearLayoutManagerReview.setOrientation(LinearLayout.VERTICAL);
        mReviewRecycleView.setLayoutManager(linearLayoutManagerReview);
        mReviewRecycleView.setHasFixedSize(true);
        mReviewRecycleView.setNestedScrollingEnabled(false);
        mReviewAdapter = new ReviewAdapter(LIST_OF_REVIEWS);
        mReviewRecycleView.setAdapter(mReviewAdapter);


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
                default:
                    Toast.makeText(this, "Default", Toast.LENGTH_LONG).show();
        }
    }

    private void addMovieToDB(){
        mMovieData = new FavoritesMovieDataDB(mMovieTitle,
                mMovieReleaseDate, mMovieVoteAverage, mMovieOverview, mImageThumbnail, true);
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
}
