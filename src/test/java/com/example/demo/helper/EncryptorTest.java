package com.example.demo.helper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EncryptorTest {

    @Test
    public void encryptTest(){
        Encryptor encryptor = new Encryptor();
        Assertions.assertEquals("aGVsbG8gd29ybGQ",encryptor.encode("hello world"));
    }

    @Test
    public void decryptTest(){
        Encryptor encryptor = new Encryptor();
        Assertions.assertEquals("hello world",encryptor.decode("aGVsbG8gd29ybGQ"));
    }
}
