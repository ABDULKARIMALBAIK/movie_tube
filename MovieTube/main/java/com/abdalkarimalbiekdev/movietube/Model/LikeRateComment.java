package com.abdalkarimalbiekdev.movietube.Model;

import io.reactivex.annotations.Nullable;

public class LikeRateComment {

    @Nullable
    private String liked;
    @Nullable
    private String rating_entertament;
    @Nullable
    private String rating_resolution;
    @Nullable
    private String rating_perform_actors;
    @Nullable
    private String comment;

    public LikeRateComment() {
    }

    public LikeRateComment(String liked, String rating_entertament, String rating_resolution, String rating_perform_actors, String comment) {
        this.liked = liked;
        this.rating_entertament = rating_entertament;
        this.rating_resolution = rating_resolution;
        this.rating_perform_actors = rating_perform_actors;
        this.comment = comment;
    }

    public String getLiked() {
        return liked;
    }

    public void setLiked(String liked) {
        this.liked = liked;
    }

    public String getRating_entertament() {
        return rating_entertament;
    }

    public void setRating_entertament(String rating_entertament) {
        this.rating_entertament = rating_entertament;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
