package com.ftn.sbnz.exception;

public class JwtExpiredException extends Exception{
    public JwtExpiredException(String message) {
        super(message);
    }
}
