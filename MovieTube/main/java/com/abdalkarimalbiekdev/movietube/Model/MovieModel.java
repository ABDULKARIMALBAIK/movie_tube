package com.abdalkarimalbiekdev.movietube.Model;

import io.reactivex.annotations.Nullable;

public class MovieModel {

    @Nullable
    private String category_name;
    @Nullable
    private int id;
    @Nullable
    private String movie_name;
    @Nullable
    private String description;
    @Nullable
    private String rate;
    @Nullable
    private String image_path;


    public MovieModel() {
    }

    public MovieModel(String category_name, String movie_name, String description, String rate, String image_path , int id) {
        this.category_name = category_name;
        this.movie_name = movie_name;
        this.description = description;
        this.rate = rate;
        this.image_path = image_path;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getMovie_name() {
        return movie_name;
    }

    public void setMovie_name(String movie_name) {
        this.movie_name = movie_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }
}
