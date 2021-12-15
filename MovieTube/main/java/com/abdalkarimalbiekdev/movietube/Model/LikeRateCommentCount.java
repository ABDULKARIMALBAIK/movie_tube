package com.abdalkarimalbiekdev.movietube.Model;

import io.reactivex.annotations.Nullable;

public class LikeRateCommentCount {

    @Nullable
    private int id;
    @Nullable
    private String count_comments;
    @Nullable
    private String count_like;
    @Nullable
    private String count_dislike;
    @Nullable
    private String count_ratings;
    @Nullable
    private String avg_rating_entertament;
    @Nullable
    private String rating_resolution;
    @Nullable
    private String rating_perform_actors;

    public LikeRateCommentCount() {
    }

    public LikeRateCommentCount(int id, String count_comments, String count_like, String count_dislike, String count_ratings, String avg_rating_entertament, String rating_resolution, String rating_perform_actors) {
        this.id = id;
        this.count_comments = count_comments;
        this.count_like = count_like;
        this.count_dislike = count_dislike;
        this.count_ratings = count_ratings;
        this.avg_rating_entertament = avg_rating_entertament;
        this.rating_resolution = rating_resolution;
        this.rating_perform_actors = rating_perform_actors;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCount_comments() {
        return count_comments;
    }

    public void setCount_comments(String count_comments) {
        this.count_comments = count_comments;
    }

    public String getCount_like() {
        return count_like;
    }

    public void setCount_like(String count_like) {
        this.count_like = count_like;
    }

    public String getCount_dislike() {
        return count_dislike;
    }

    public void setCount_dislike(String count_dislike) {
        this.count_dislike = count_dislike;
    }

    public String getCount_ratings() {
        return count_ratings;
    }

    public void setCount_ratings(String count_ratings) {
        this.count_ratings = count_ratings;
    }

    public String getAvg_rating_entertament() {
        return avg_rating_entertament;
    }

    public void setAvg_rating_entertament(String avg_rating_entertament) {
        this.avg_rating_entertament = avg_rating_entertament;
    }

    public String getRating_resolution() {
        return rating_resolution;
    }

    public void setRating_resolution(String rating_resolution) {
        this.rating_resolution = rating_resolution;
    }

    public String getRating_perform_actors() {
        return rating_perform_actors;
    }

    public void setRating_perform_actors(String rating_perform_actors) {
        this.rating_perform_actors = rating_perform_actors;
    }
}
