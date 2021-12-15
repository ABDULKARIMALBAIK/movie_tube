package com.abdalkarimalbiekdev.movietube.Model;

import io.reactivex.annotations.Nullable;

public class ServerResponse {

    @Nullable
    public String result;
    @Nullable
    public String errorMessage;

    public ServerResponse() {
    }

    public ServerResponse(String result, String errorMessage) {
        this.result = result;
        this.errorMessage = errorMessage;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
