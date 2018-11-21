package com.example.android.popularmoviesapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private int mReviewItems;

    public ReviewAdapter(int noOfReviews){
        mReviewItems = noOfReviews;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.review_list_item, parent, false);
        ReviewViewHolder reviewViewHolder = new ReviewViewHolder(view);

        return reviewViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mReviewItems;
    }


    class ReviewViewHolder extends RecyclerView.ViewHolder{

        TextView mReviewAuthorTextView;
        TextView mReviewDescriptionTextView;

        public ReviewViewHolder(View itemView) {
            super(itemView);

            mReviewAuthorTextView = (TextView) itemView.findViewById(R.id.tv_review_author);
            mReviewDescriptionTextView = (TextView) itemView.findViewById(R.id.tv_review_description);
        }

        void bind(int listIndex){
            mReviewAuthorTextView.setText(String.valueOf(listIndex));
        }
    }
}
