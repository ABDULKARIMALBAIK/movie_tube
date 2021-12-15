package com.abdalkarimalbiekdev.movietube.Model;

import java.util.Date;

import io.reactivex.annotations.Nullable;

public class MovieDetailsModel {

    @Nullable
    public int id;
    @Nullable
    public String name;
    @Nullable
    public String description;
    @Nullable
    public String rate;
    @Nullable
    public String movie_path;
    @Nullable
    public String trailer_path;
    @Nullable
    public String release_date;
    @Nullable
    public String banner;
    @Nullable
    public String poster;


    public MovieDetailsModel() {
    }

    public MovieDetailsModel(int id, String name, String description, String rate, String movie_path, String trailer_path, String release_date, String banner, String poster) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.rate = rate;
        this.movie_path = movie_path;
        this.trailer_path = trailer_path;
        this.release_date = release_date;
        this.banner = banner;
        this.poster = poster;
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

    public String getMovie_path() {
        return movie_path;
    }

    public void setMovie_path(String movie_path) {
        this.movie_path = movie_path;
    }

    public String getTrailer_path() {
        return trailer_path;
    }

    public void setTrailer_path(String trailer_path) {
        this.trailer_path = trailer_path;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }
}
