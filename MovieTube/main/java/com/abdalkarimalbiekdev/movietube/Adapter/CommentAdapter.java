package com.abdalkarimalbiekdev.movietube.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.abdalkarimalbiekdev.movietube.Model.CommentItem;
import com.abdalkarimalbiekdev.movietube.R;
import com.abdalkarimalbiekdev.movietube.Security.AES;
import com.abdalkarimalbiekdev.movietube.Utils.Common;
import com.abdalkarimalbiekdev.movietube.ViewHolder.CommentViewHolder;
import com.bumptech.glide.Glide;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CommentAdapter extends RecyclerView.Adapter<CommentViewHolder> {


    Context context;
    List<CommentItem> commentItems;

    public CommentAdapter(Context context, List<CommentItem> commentItems) {
        this.context = context;
        this.commentItems = commentItems;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item , parent ,false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {


        try {
            holder.txtNameCommentItem.setText(AES.decrypt(commentItems.get(position).getName()));
            holder.txtContextCommentItem.setText(AES.decrypt(commentItems.get(position).getComment()));

            Glide.with(context)
                    .load(Common.BASE_URL + "...................." + AES.decrypt(commentItems.get(position).getImage_path()))
                    .centerCrop()
                    .placeholder(R.drawable.ic_person_yallow_24dp)
                    .skipMemoryCache(true)
                    .into(holder.imgCommentItem);
        }
        catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }



    }

    @Override
    public int getItemCount() {
        return commentItems.size();
    }
}
