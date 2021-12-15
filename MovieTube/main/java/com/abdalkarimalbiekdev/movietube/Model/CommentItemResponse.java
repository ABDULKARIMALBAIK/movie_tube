package com.abdalkarimalbiekdev.movietube.Model;

import java.util.List;

import io.reactivex.annotations.Nullable;

public class CommentItemResponse {

    @Nullable
    public List<CommentItem> result;
    @Nullable
    public String errorMessage;

    public CommentItemResponse() {
    }

    public CommentItemResponse(List<CommentItem> result, String errorMessage) {
        this.result = result;
        this.errorMessage = errorMessage;
    }

    public List<CommentItem> getResult() {
        return result;
    }

    public void setResult(List<CommentItem> result) {
        this.result = result;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
