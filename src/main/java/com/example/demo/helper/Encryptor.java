package com.example.demo.helper;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;


@Component
public class Encryptor {
    public String encode(String input){
        return Base64.getEncoder().withoutPadding().encodeToString(input.getBytes(StandardCharsets.UTF_8));
    }

    public String decode(String input){
        return new String(Base64.getDecoder().decode(input));
    }
}
