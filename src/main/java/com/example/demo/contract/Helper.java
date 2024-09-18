package com.example.demo.contract;

import com.example.demo.model.ErrorResponse;

public interface Helper {

    public ErrorResponse generateError(String msg, Integer code);

    public String generateToken(String subject);

    public String getNow();

    public String extractToken(String header);
}
