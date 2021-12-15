package com.abdalkarimalbiekdev.movietube.ViewHolder;

import android.view.View;
import android.widget.TextView;

import com.abdalkarimalbiekdev.movietube.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class CommentViewHolder extends RecyclerView.ViewHolder {

    public CircleImageView imgCommentItem;
    public TextView txtNameCommentItem , txtContextCommentItem;


    public CommentViewHolder(@NonNull View itemView) {
        super(itemView);

        imgCommentItem = itemView.findViewById(R.id.imgCommentItem);
        txtNameCommentItem = itemView.findViewById(R.id.txtNameCommentItem);
        txtContextCommentItem = itemView.findViewById(R.id.txtContextCommentItem);
    }
}
