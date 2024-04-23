package com.jwt.exception;

public class UserIsAlreadyRegisteredException extends Throwable {
    public UserIsAlreadyRegisteredException(String msg){
        super(msg);
    }
}
