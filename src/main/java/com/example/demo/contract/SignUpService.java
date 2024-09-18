package com.example.demo.contract;

import com.example.demo.exception.InvalidFormatException;
import com.example.demo.exception.UserAlreadyExistsException;
import com.example.demo.model.SignUpRequestDto;
import com.example.demo.model.SignUpResponseDto;

public interface SignUpService {

    public SignUpResponseDto signUp(SignUpRequestDto request) throws InvalidFormatException, UserAlreadyExistsException;
}
