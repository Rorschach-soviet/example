package com.example.demo.contract;

import com.example.demo.exception.InvalidTokenException;
import com.example.demo.model.LoginResponseDto;

public interface LoginService {

    public LoginResponseDto login(String token) throws InvalidTokenException;
}
