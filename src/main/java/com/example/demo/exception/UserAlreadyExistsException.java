package com.example.demo.exception;

import com.example.demo.helper.Constants;

public class UserAlreadyExistsException extends Exception {

    public UserAlreadyExistsException(String msg){
        super(msg);
    }
    public Integer code(){
        return Constants.USER_EXISTS_CODE;
    }
}
