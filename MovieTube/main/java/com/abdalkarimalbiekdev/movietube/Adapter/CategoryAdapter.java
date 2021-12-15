package com.abdalkarimalbiekdev.movietube.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.abdalkarimalbiekdev.movietube.Interface.IClickListener;
import com.abdalkarimalbiekdev.movietube.Model.Category;
import com.abdalkarimalbiekdev.movietube.Movie;
import com.abdalkarimalbiekdev.movietube.R;
import com.abdalkarimalbiekdev.movietube.Security.AES;
import com.abdalkarimalbiekdev.movietube.Utils.Common;
import com.abdalkarimalbiekdev.movietube.ViewHolder.CategoryViewHolder;
import com.bumptech.glide.Glide;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryViewHolder> {

    public Context context;
    public List<Category> categories;

    public CategoryAdapter(Context context, List<Category> categories) {
        this.context = context;
        this.categories = categories;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item , parent , false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {

        try {
            holder.txtCategory.setText(AES.decrypt(categories.get(position).getName()));
            Glide.with(context)
                    .load(Common.BASE_URL + "..............." + AES.decrypt(categories.get(position).getImage_path()))
                    .centerCrop()
                    .skipMemoryCache(true)
                    .into(holder.imgCategory);

        }
        catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }



        holder.setiClickListener(new IClickListener() {
            @Override
            public void onCLick(View view, int position, boolean isLongClick) {

                Intent intent = new Intent(context , Movie.class);
                intent.putExtra("category_id" , categories.get(position).getId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}
