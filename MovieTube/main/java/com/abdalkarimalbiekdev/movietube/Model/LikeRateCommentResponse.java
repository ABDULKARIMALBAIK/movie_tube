package com.abdalkarimalbiekdev.movietube.Model;

import io.reactivex.annotations.Nullable;

public class LikeRateCommentResponse {

    @Nullable
    public  LikeRateComment result;
    @Nullable
    public String errorMessage;

    public LikeRateCommentResponse() {
    }

    public LikeRateCommentResponse(LikeRateComment result, String errorMessage) {
        this.result = result;
        this.errorMessage = errorMessage;
    }

    public LikeRateComment getResult() {
        return result;
    }

    public void setResult(LikeRateComment result) {
        this.result = result;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
