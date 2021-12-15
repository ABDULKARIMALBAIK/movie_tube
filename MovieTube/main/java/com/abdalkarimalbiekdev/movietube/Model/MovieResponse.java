package com.abdalkarimalbiekdev.movietube.Model;

import java.util.List;

import io.reactivex.annotations.Nullable;

public class MovieResponse {

    @Nullable
    public List<MovieModel> result;
    @Nullable
    public String errorMessage;

    public MovieResponse() {
    }

    public MovieResponse(List<MovieModel> result, String errorMessage) {
        this.result = result;
        this.errorMessage = errorMessage;
    }

    public List<MovieModel> getResult() {
        return result;
    }

    public void setResult(List<MovieModel> result) {
        this.result = result;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
