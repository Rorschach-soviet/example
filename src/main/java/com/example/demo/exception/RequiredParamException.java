package com.example.demo.exception;

import com.example.demo.helper.Constants;

public class RequiredParamException extends Exception {
    public RequiredParamException(String msg){
        super(msg);
    }

    public Integer code(){
        return Constants.REQUIRED_ERROR_CODE;
    }
}
