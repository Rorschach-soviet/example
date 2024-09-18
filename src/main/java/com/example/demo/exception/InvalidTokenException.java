package com.example.demo.exception;

import com.example.demo.helper.Constants;

public class InvalidTokenException extends Exception{
    public InvalidTokenException(String msg){
        super(msg);
    }
    public Integer code(){
        return Constants.TOKEN_ERROR_CODE;
    }
}
