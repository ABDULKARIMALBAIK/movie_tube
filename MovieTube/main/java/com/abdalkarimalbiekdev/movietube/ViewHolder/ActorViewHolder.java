package com.abdalkarimalbiekdev.movietube.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.abdalkarimalbiekdev.movietube.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ActorViewHolder extends RecyclerView.ViewHolder {

    public ImageView imgActorItem;
    public TextView txtActorItem;

    public ActorViewHolder(@NonNull View itemView) {
        super(itemView);

        imgActorItem = itemView.findViewById(R.id.imgActorItem);
        txtActorItem = itemView.findViewById(R.id.txtActorItem);
    }
}
