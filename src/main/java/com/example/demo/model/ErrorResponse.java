package com.example.demo.model;

import java.util.List;

public class ErrorResponse {

    private List<ErrorDto> error;

    public ErrorResponse(){

    }

    public ErrorResponse(List<ErrorDto> error) {
        this.error = error;
    }

    public List<ErrorDto> getError() {
        return error;
    }

    public void setError(List<ErrorDto> error) {
        this.error = error;
    }
}
