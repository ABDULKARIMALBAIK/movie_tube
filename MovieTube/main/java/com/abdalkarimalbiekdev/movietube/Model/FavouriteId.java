package com.abdalkarimalbiekdev.movietube.Model;

import io.reactivex.annotations.Nullable;

public class FavouriteId {

    @Nullable
    public String errorMessage;
    @Nullable
    public int value;

    public FavouriteId() {
    }

    public FavouriteId(String errorMessage, int value) {
        this.errorMessage = errorMessage;
        this.value = value;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
