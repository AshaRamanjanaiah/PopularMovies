package com.example.android.popularmoviesapp;

/**
 * Created by Asha Ramanjanaiah on 10/18/2018
 */

import android.content.Context;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.android.popularmoviesapp.model.MovieData;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends ArrayAdapter<MovieData> {

    private static final String TAG = ImageAdapter.class.getSimpleName();

    private final static String BASE_URL = "http://image.tmdb.org/t/p/w185";

    public ImageAdapter(@NonNull Context context, int resource, @NonNull List<MovieData> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        MovieData movieData = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.moviethumbnl_items, parent, false);
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.iv_thumbnails);

        try {
            Picasso.with(convertView.getContext())
                    .load(BASE_URL + movieData.getImageThumbnail())
                    .error(R.drawable.sample_5)
                    .placeholder(R.drawable.sample_5)
                    .into(imageView);
        }catch (Exception e){
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        }
        return convertView;
    }
}
