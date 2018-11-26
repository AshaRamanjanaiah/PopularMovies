package com.example.android.popularmoviesapp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmoviesapp.model.TrailerData;

import java.util.ArrayList;
import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {


    private List<TrailerData> mTrailerDataList = new ArrayList<>();

        public TrailerAdapter(List<TrailerData> trailerDataList){
            mTrailerDataList.addAll(trailerDataList);
        }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(R.layout.trailer_list_item, parent, shouldAttachToParentImmediately);
        TrailerViewHolder trailerViewHolder = new TrailerViewHolder(view);

        return trailerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {

        if (null == mTrailerDataList) return 0;
        return mTrailerDataList.size();
    }

    class TrailerViewHolder extends RecyclerView.ViewHolder{

        ImageView mTrailerIcon;
        TextView mTrailerTitle;

        public TrailerViewHolder(View itemView) {
            super(itemView);

            mTrailerIcon = (ImageView) itemView.findViewById(R.id.iv_trailer_icon);
            mTrailerTitle = (TextView) itemView.findViewById(R.id.tv_trailer_title);

        }

        void bind(int listIndex){
           String name =  mTrailerDataList.get(listIndex).getName();
            mTrailerTitle.setText(name);

            String key = mTrailerDataList.get(listIndex).getKey();
        }
    }
}
