package com.abdalkarimalbiekdev.movietube.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.abdalkarimalbiekdev.movietube.Interface.IClickListener;
import com.abdalkarimalbiekdev.movietube.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public ImageView imgMovie;
    public TextView txtName , txtDescription;
    public RatingBar ratingMovie;

    private IClickListener iClickListener;

    public void setiClickListener(IClickListener iClickListener) {
        this.iClickListener = iClickListener;
    }

    public MovieViewHolder(@NonNull View itemView) {
        super(itemView);

        imgMovie = itemView.findViewById(R.id.imgMovie);
        txtName = itemView.findViewById(R.id.txtName);
        txtDescription = itemView.findViewById(R.id.txtDescription);
        ratingMovie = itemView.findViewById(R.id.ratingMovie);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        iClickListener.onCLick(v , getAdapterPosition() , false);
    }

}
