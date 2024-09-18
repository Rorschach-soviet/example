package com.example.demo.contract;

import com.example.demo.exception.InvalidFormatException;
import com.example.demo.exception.RequiredParamException;
import com.example.demo.model.SignUpRequestDto;

public interface Validator {

    public void validate(SignUpRequestDto request) throws InvalidFormatException, RequiredParamException;
}
