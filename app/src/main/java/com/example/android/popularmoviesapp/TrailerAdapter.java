package com.example.android.popularmoviesapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {

    private int mTrailerItems;

    public TrailerAdapter(int noOftrailers){
        mTrailerItems = noOftrailers;
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
        return mTrailerItems;
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
            mTrailerTitle.setText(String.valueOf(listIndex));
        }
    }
}
