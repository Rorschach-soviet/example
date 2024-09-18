package com.example.demo.helper;

import com.example.demo.contract.Validator;
import com.example.demo.exception.InvalidFormatException;
import com.example.demo.exception.RequiredParamException;
import com.example.demo.model.SignUpRequestDto;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class ValidatorImpl implements Validator {

    public final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile(Constants.EMAIL_VALIDATOR_PATTERN, Pattern.CASE_INSENSITIVE);

    public final Pattern VALID_PASSWORD_ADDRESS_REGEX =
            Pattern.compile(Constants.PASSWORD_VALIDATOR_PATTERN, Pattern.CASE_INSENSITIVE);
    @Override
    public void validate(SignUpRequestDto request) throws InvalidFormatException, RequiredParamException {
        if(request.getEmail() == null){
            throw new RequiredParamException(Constants.EMAIL_IS_REQUIRED);
        }

        if(!VALID_EMAIL_ADDRESS_REGEX.matcher(request.getEmail()).matches()){
            throw new InvalidFormatException(Constants.EMAIL_ERROR);
        }

        if(request.getPassword() == null){
            throw new RequiredParamException(Constants.PASSWORD_IS_REQUIRED);
        }

        if(!VALID_PASSWORD_ADDRESS_REGEX.matcher(request.getPassword()).matches()){
            throw new InvalidFormatException(Constants.PASSWORD_ERROR);
        }
    }
}
