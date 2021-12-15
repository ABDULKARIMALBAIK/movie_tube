package com.abdalkarimalbiekdev.movietube.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.abdalkarimalbiekdev.movietube.Interface.IClickListener;
import com.abdalkarimalbiekdev.movietube.Model.MovieModel;
import com.abdalkarimalbiekdev.movietube.MovieDetails;
import com.abdalkarimalbiekdev.movietube.R;
import com.abdalkarimalbiekdev.movietube.Security.AES;
import com.abdalkarimalbiekdev.movietube.Utils.Common;
import com.abdalkarimalbiekdev.movietube.ViewHolder.MovieViewHolder;
import com.bumptech.glide.Glide;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MovieAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    Context context;
    List<MovieModel> movieModels;

    public MovieAdapter(Context context, List<MovieModel> movieModels) {
        this.context = context;
        this.movieModels = movieModels;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item , parent , false);

        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {

        try {
            holder.txtName.setText(AES.decrypt(movieModels.get(position).getMovie_name()));
            holder.txtDescription.setText(AES.decrypt(movieModels.get(position).getDescription()).substring(0 , 40) + "....");
            holder.ratingMovie.setRating(Float.parseFloat(AES.decrypt(movieModels.get(position).getRate())));

            Glide.with(context)
                    .load(Common.BASE_URL + "...................." + AES.decrypt(movieModels.get(position).getImage_path()))
                    .centerCrop()
                    .skipMemoryCache(true)
                    .into(holder.imgMovie);

        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }



        holder.setiClickListener(new IClickListener() {
            @Override
            public void onCLick(View view, int position, boolean isLongClick) {

                Intent intent = new Intent(context , MovieDetails.class);
                intent.putExtra("movie_id" , movieModels.get(position).getId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return movieModels.size();
    }
}
