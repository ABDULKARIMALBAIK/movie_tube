package com.abdalkarimalbiekdev.movietube.Model;

import io.reactivex.annotations.Nullable;

public class CommentItem {

    @Nullable
    private int id;
    @Nullable
    private String name;
    @Nullable
    private String image_path;
    @Nullable
    private String comment;

    public CommentItem() {
    }

    public CommentItem(int id, String name, String image_path, String comment) {
        this.id = id;
        this.name = name;
        this.image_path = image_path;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
