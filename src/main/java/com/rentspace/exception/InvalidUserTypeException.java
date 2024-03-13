package com.rentspace.exception;

public class InvalidUserTypeException extends RuntimeException {

    public static final String INVALID_USER_TYPE = "Este tipo de usuário não é suportado.";

    public InvalidUserTypeException() {
        super(INVALID_USER_TYPE);
    }
}
