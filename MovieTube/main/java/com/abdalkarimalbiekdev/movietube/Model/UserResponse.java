package com.abdalkarimalbiekdev.movietube.Model;

import io.reactivex.annotations.Nullable;

public class UserResponse {

    @Nullable
    public User result;
    @Nullable
    public String errorMessage;

    public UserResponse() {
    }

    public UserResponse(User result, String errorMessage) {
        this.result = result;
        this.errorMessage = errorMessage;
    }

    public User getResult() {
        return result;
    }

    public void setResult(User result) {
        this.result = result;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
