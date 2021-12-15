package com.abdalkarimalbiekdev.movietube.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.abdalkarimalbiekdev.movietube.Model.ActorItem;
import com.abdalkarimalbiekdev.movietube.R;
import com.abdalkarimalbiekdev.movietube.Security.AES;
import com.abdalkarimalbiekdev.movietube.Utils.Common;
import com.abdalkarimalbiekdev.movietube.ViewHolder.ActorViewHolder;
import com.bumptech.glide.Glide;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ActorAdapter extends RecyclerView.Adapter<ActorViewHolder > {

    Context context;
    List<ActorItem> actorItems;

    public ActorAdapter(Context context, List<ActorItem> actorItems) {
        this.context = context;
        this.actorItems = actorItems;
    }

    @NonNull
    @Override
    public ActorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.actor_item , parent , false);
        return new ActorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActorViewHolder holder, int position) {

        try {
            holder.txtActorItem.setText(AES.decrypt(actorItems.get(position).getName()));

            Glide.with(context)
                    .load(Common.BASE_URL + "......................" + AES.decrypt(actorItems.get(position).getImage_path()))
                    .centerCrop()
                    .skipMemoryCache(true)
                    .into(holder.imgActorItem);
        }
        catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return actorItems.size();
    }
}
