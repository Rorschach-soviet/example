package com.example.demo.helper;

public class Constants {

    public static final Integer FORMAT_ERROR_CODE = 1000;
    public static final Integer REQUIRED_ERROR_CODE = 2000;
    public static final Integer TOKEN_ERROR_CODE=3000;
    public static final Integer USER_EXISTS_CODE=4000;

    public static final String EMAIL_ERROR="Formato de email incorrecto";
    public static final String PASSWORD_ERROR = "Formato de password incorrecto";
    public static final String TOKEN_ERROR ="El token es invalido";

    public static final String USER_EXISTS_ERROR="El usuario ya existe";
    public static final String EMAIL_VALIDATOR_PATTERN = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
    public static final String PASSWORD_VALIDATOR_PATTERN ="^(?=.*[a-z])[a-z]*[A-Z][a-z]*$|(?=^(?:\\D*\\d\\D*){2}$).{8,12}$";
    public static final String EMAIL_IS_REQUIRED ="El campo email es requerido";
    public static final String PASSWORD_IS_REQUIRED ="El campo password es requerido";
}
