package com.abdalkarimalbiekdev.movietube.Model;

import io.reactivex.annotations.Nullable;

public class MovieDetailsModelResponse {

    @Nullable
    public MovieDetailsModel result;
    @Nullable
    public String errorMessage;

    public MovieDetailsModelResponse() {
    }

    public MovieDetailsModelResponse(MovieDetailsModel result, String errorMessage) {
        this.result = result;
        this.errorMessage = errorMessage;
    }

    public MovieDetailsModel getResult() {
        return result;
    }

    public void setResult(MovieDetailsModel result) {
        this.result = result;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
