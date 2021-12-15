package com.abdalkarimalbiekdev.movietube.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.abdalkarimalbiekdev.movietube.Model.BestMovieImage;
import com.abdalkarimalbiekdev.movietube.MovieDetails;
import com.abdalkarimalbiekdev.movietube.R;
import com.abdalkarimalbiekdev.movietube.Security.AES;
import com.abdalkarimalbiekdev.movietube.Utils.Common;
import com.abdalkarimalbiekdev.movietube.ViewHolder.BestMoviesSliderViewHolder;
import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

public class BestMoviesSliderAdapter extends SliderViewAdapter<BestMoviesSliderViewHolder> {

    Context context;
    List<BestMovieImage> images;

    public BestMoviesSliderAdapter(Context context, List<BestMovieImage> images) {
        this.context = context;
        this.images = images;
    }

    @Override
    public BestMoviesSliderViewHolder onCreateViewHolder(ViewGroup parent) {

        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_item, null);
        return new BestMoviesSliderViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(final BestMoviesSliderViewHolder viewHolder, final int position) {

        try {
            Glide.with(context)
                    .load(Common.BASE_URL + "........................." + AES.decrypt(images.get(position).getImage_path()))
                    .centerCrop()
                    .skipMemoryCache(true)
                    .into(viewHolder.sliderImage);

            viewHolder.txt_slider_item.setText(AES.decrypt(images.get(position).getMovie_name()));
        }
        catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }


        viewHolder.sliderImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context , MovieDetails.class);
                intent.putExtra("movie_id" , images.get(position).getId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getCount() {
        return images.size();
    }
}
