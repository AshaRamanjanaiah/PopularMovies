package com.example.android.popularmoviesapp;

/**
 * Created by Asha Ramanjanaiah on 10/18/2018
 */

import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.android.popularmoviesapp.NetworkUtils.Constant;

public class MainActivity extends AppCompatActivity {

    private Bundle bundle;
    private String sortBy = Constant.SORTBY_BASE;
    private MainActivityFragment mainActivityFragment = new MainActivityFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null && savedInstanceState.containsKey(Constant.SORTBY) ){
            sortBy = savedInstanceState.getString(Constant.SORTBY);
        }else{
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.main_container, mainActivityFragment);
            fragmentTransaction.commit();
        }

        bundle = new Bundle();
        bundle.putString(Constant.SORTBY, sortBy);
        mainActivityFragment.setArguments(bundle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();

        menuInflater.inflate(R.menu.movieinfo, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id){
            case R.id.it_most_popular:
                sortBy = Constant.SORYBY_POPULARITY;
                break;
            case R.id.it_top_rated:
                sortBy = Constant.SORYBY_HIGH_RATED;
                break;
            case R.id.it_favorites:
                sortBy = Constant.FAVORITES;
                break;
                default:
                    sortBy = Constant.SORTBY_BASE;
                    break;
        }

        bundle.putString(Constant.SORTBY, sortBy);
        mainActivityFragment = new MainActivityFragment();
        mainActivityFragment.setArguments(bundle);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.main_container, mainActivityFragment).addToBackStack(null).commit();

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(Constant.SORTBY, sortBy);
        super.onSaveInstanceState(outState);
    }
}


