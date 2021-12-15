package com.abdalkarimalbiekdev.movietube.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.abdalkarimalbiekdev.movietube.Interface.IClickListener;
import com.abdalkarimalbiekdev.movietube.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView txtCategory;
    public ImageView imgCategory;
    private IClickListener iClickListener;

    public void setiClickListener(IClickListener iClickListener) {
        this.iClickListener = iClickListener;
    }

    public CategoryViewHolder(@NonNull View itemView) {
        super(itemView);

        txtCategory = itemView.findViewById(R.id.txtCategory);
        imgCategory = itemView.findViewById(R.id.imgCategory);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        iClickListener.onCLick(v , getAdapterPosition() ,false);
    }
}
