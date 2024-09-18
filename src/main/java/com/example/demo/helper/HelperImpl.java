package com.example.demo.helper;

import com.example.demo.contract.Helper;
import com.example.demo.model.ErrorDto;
import com.example.demo.model.ErrorResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;

@Component
public class HelperImpl implements Helper {

    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    // Estableceremos la validez de nuestro token por un lapso de 6 horas (milisegundos)
    private static final long expirationTime = 21600000;


    @Override
    public ErrorResponse generateError(String msg, Integer code) {
        return generateErrorResponse(new ErrorDto(
                new Date().toString(),
                code,
                msg
        ));
    }

    public String generateToken(String subject) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationTime);
        return Jwts.builder()
                .setSubject(subject)
                .setExpiration(expiryDate)
                .signWith(key)
                .compact();
    }

    public String getNow(){
        return new Date().toString();
    }

    @Override
    public String extractToken(String header) {
        return header.replace("Bearer","").trim();
    }

    private ErrorResponse generateErrorResponse(ErrorDto error){
        return new ErrorResponse(Arrays.asList(error));
    }
}
