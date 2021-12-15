package com.abdalkarimalbiekdev.movietube.Model;

import java.util.List;

import io.reactivex.annotations.Nullable;

public class BestMovieImageResponse {

    @Nullable
    public List<BestMovieImage> result;
    @Nullable
    public String errorMessage;

    public BestMovieImageResponse() {
    }

    public BestMovieImageResponse(List<BestMovieImage> result, String errorMessage) {
        this.result = result;
        this.errorMessage = errorMessage;
    }

    public List<BestMovieImage> getResult() {
        return result;
    }

    public void setResult(List<BestMovieImage> result) {
        this.result = result;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
