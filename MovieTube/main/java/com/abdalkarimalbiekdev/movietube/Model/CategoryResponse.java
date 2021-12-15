package com.abdalkarimalbiekdev.movietube.Model;

import java.util.List;

public class CategoryResponse {

    public List<Category> result;
    public String errorMessage;

    public CategoryResponse() {
    }

    public CategoryResponse(List<Category> result, String errorMessage) {
        this.result = result;
        this.errorMessage = errorMessage;
    }

    public List<Category> getResult() {
        return result;
    }

    public void setResult(List<Category> result) {
        this.result = result;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
