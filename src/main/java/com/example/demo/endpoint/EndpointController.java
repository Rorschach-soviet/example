package com.example.demo.endpoint;

import com.example.demo.contract.Helper;
import com.example.demo.contract.LoginService;
import com.example.demo.contract.SignUpService;
import com.example.demo.contract.Validator;
import com.example.demo.exception.InvalidFormatException;
import com.example.demo.exception.InvalidTokenException;
import com.example.demo.exception.RequiredParamException;
import com.example.demo.exception.UserAlreadyExistsException;
import com.example.demo.model.LoginResponseDto;
import com.example.demo.model.SignUpRequestDto;
import com.example.demo.model.SignUpResponseDto;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class EndpointController {
    @Autowired
    SignUpService signUpService;

    @Autowired
    LoginService loginService;
    @Autowired
    Helper helper;

    @Autowired
    Validator validator;

    @PostMapping(value="/sign-up",consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequestDto request){

        try {
            validator.validate(request);
            return new ResponseEntity<>(signUpService.signUp(request), HttpStatus.CREATED);
        } catch (InvalidFormatException e) {
           return new ResponseEntity<>(helper.generateError(e.getMessage(),e.code())
                   ,HttpStatus.NOT_ACCEPTABLE);
        }catch (RequiredParamException e) {
            return new ResponseEntity<>(helper.generateError(e.getMessage(),e.code())
                    ,HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE);
        }catch (UserAlreadyExistsException e){
            return new ResponseEntity<>(helper.generateError(e.getMessage(),e.code())
                    ,HttpStatus.CONFLICT);
        }
    }

    @GetMapping(value="/login", produces = "application/json")
    public ResponseEntity<?> login(@RequestHeader("Authorization") String auth){
        try {
            LoginResponseDto user = loginService.login(helper.extractToken(auth));
            return new ResponseEntity<>(user
                    ,HttpStatus.OK);
        } catch (InvalidTokenException e) {
            return new ResponseEntity<>(helper.generateError(e.getMessage(),e.code())
                    ,HttpStatus.FORBIDDEN);
        }


    }
}
