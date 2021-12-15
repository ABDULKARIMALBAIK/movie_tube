package com.abdalkarimalbiekdev.movietube.Model;

import java.util.List;

import io.reactivex.annotations.Nullable;

public class MovieImagesResponse {

    @Nullable
    public List<String> result;
    @Nullable
    public String errorMessage;

    public MovieImagesResponse() {
    }

    public MovieImagesResponse(List<String> result, String errorMessage) {
        this.result = result;
        this.errorMessage = errorMessage;
    }

    public List<String> getResult() {
        return result;
    }

    public void setResult(List<String> result) {
        this.result = result;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
