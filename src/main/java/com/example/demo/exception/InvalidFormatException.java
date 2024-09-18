package com.example.demo.exception;

import com.example.demo.helper.Constants;

public class InvalidFormatException extends Exception{
    public InvalidFormatException(String msg){
        super(msg);
    }

    public Integer code(){
        return Constants.FORMAT_ERROR_CODE;
    }
}
