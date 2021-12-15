package com.abdalkarimalbiekdev.movietube.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.abdalkarimalbiekdev.movietube.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

import org.w3c.dom.Text;

public class BestMoviesSliderViewHolder extends SliderViewAdapter.ViewHolder {

    public ImageView sliderImage;
    public TextView txt_slider_item;

    public BestMoviesSliderViewHolder(View itemView) {
        super(itemView);

        sliderImage = itemView.findViewById(R.id.image_slider_item);
        txt_slider_item = itemView.findViewById(R.id.txt_slider_item);
    }
}
