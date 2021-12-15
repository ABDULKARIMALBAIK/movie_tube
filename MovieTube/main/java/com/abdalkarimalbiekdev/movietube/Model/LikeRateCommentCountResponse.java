package com.abdalkarimalbiekdev.movietube.Model;

import java.util.List;

import io.reactivex.annotations.Nullable;

public class LikeRateCommentCountResponse {

    @Nullable
    public List<LikeRateCommentCount> result;
    @Nullable
    public String errorMessage;

    public LikeRateCommentCountResponse() {
    }

    public LikeRateCommentCountResponse(List<LikeRateCommentCount> result, String errorMessage) {
        this.result = result;
        this.errorMessage = errorMessage;
    }

    public List<LikeRateCommentCount> getResult() {
        return result;
    }

    public void setResult(List<LikeRateCommentCount> result) {
        this.result = result;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
