package com.example.demo.helper;

import com.example.demo.contract.Validator;
import com.example.demo.exception.InvalidFormatException;
import com.example.demo.exception.RequiredParamException;
import com.example.demo.model.SignUpRequestDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ValidatorTest {
    @Autowired
    Validator validator;

    @Test
    public void validate(){
        Assertions.assertDoesNotThrow(()->{
            this.validator.validate(getRequest());
        });
    }

    @Test
    public void failValidateEmail(){
        SignUpRequestDto request = getRequest();
        request.setEmail(null);

        Assertions.assertThrows(RequiredParamException.class,()->{
            this.validator.validate(request);
        });
    }

    @Test
    public void failValidatePassword(){
        SignUpRequestDto request = getRequest();
        request.setPassword(null);

        Assertions.assertThrows(RequiredParamException.class,()->{
            this.validator.validate(request);
        });
    }

    @Test
    public void failValidateEmailFormat(){
        SignUpRequestDto request = getRequest();
        request.setEmail("lololo.com");

        Assertions.assertThrows(InvalidFormatException.class,()->{
            this.validator.validate(request);
        });
    }

    @Test
    public void failValidatePasswordFormat(){
        SignUpRequestDto request = getRequest();
        request.setPassword("lololo@@*%");

        Assertions.assertThrows(InvalidFormatException.class,()->{
            this.validator.validate(request);
        });
    }

    private SignUpRequestDto getRequest(){
        SignUpRequestDto response = new SignUpRequestDto();
        response.setEmail("foobar@mymail.com");
        response.setName("John");
        response.setPassword("Fdelskdier23");

        return response;
    }

}
