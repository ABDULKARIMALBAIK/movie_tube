package com.abdalkarimalbiekdev.movietube.ViewHolder;

import android.view.View;
import android.widget.ImageView;

import com.abdalkarimalbiekdev.movietube.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PhotoViewHolder extends RecyclerView.ViewHolder {

    public ImageView imgPhotoItem;

    public PhotoViewHolder(@NonNull View itemView) {
        super(itemView);

        imgPhotoItem = itemView.findViewById(R.id.imgPhotoItem);
    }
}
