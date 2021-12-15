package com.abdalkarimalbiekdev.movietube.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.abdalkarimalbiekdev.movietube.R;
import com.abdalkarimalbiekdev.movietube.Security.AES;
import com.abdalkarimalbiekdev.movietube.Utils.Common;
import com.abdalkarimalbiekdev.movietube.ViewHolder.PhotoViewHolder;
import com.bumptech.glide.Glide;
import com.ceylonlabs.imageviewpopup.ImagePopup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoViewHolder> {


    Context context;
    List<String> photoItems;

    public PhotoAdapter(Context context, List<String> photoItems) {
        this.context = context;
        this.photoItems = photoItems;
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_item , parent , false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {

        try {
            Glide.with(context)
                    .load(Common.BASE_URL + "..................." + AES.decrypt(photoItems.get(position)))
                    .centerCrop()
                    .skipMemoryCache(true)
                    .into(holder.imgPhotoItem);
        }
        catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        holder.imgPhotoItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ImagePopup imagePopup = new ImagePopup(context);
                imagePopup.setWindowHeight(800);
                imagePopup.setWindowWidth(800);
                imagePopup.setBackgroundColor(Color.BLACK);
                imagePopup.setFullScreen(true);
                imagePopup.setHideCloseIcon(true);
                imagePopup.setImageOnClickClose(true);

                imagePopup.initiatePopup(holder.imgPhotoItem.getDrawable()); // Load Image from Drawable
                imagePopup.viewPopup();
            }
        });

    }

    @Override
    public int getItemCount() {
        return photoItems.size();
    }
}
