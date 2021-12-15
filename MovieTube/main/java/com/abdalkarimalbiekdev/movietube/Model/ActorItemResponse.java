package com.abdalkarimalbiekdev.movietube.Model;

import java.util.List;

import io.reactivex.annotations.Nullable;

public class ActorItemResponse {

    @Nullable
    public List<ActorItem> result;
    @Nullable
    public String errorMessage;

    public ActorItemResponse() {
    }

    public ActorItemResponse(List<ActorItem> result, String errorMessage) {
        this.result = result;
        this.errorMessage = errorMessage;
    }

    public List<ActorItem> getResult() {
        return result;
    }

    public void setResult(List<ActorItem> result) {
        this.result = result;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
