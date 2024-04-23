package com.jwt.exception;

import org.springframework.security.core.AuthenticationException;

public class InCorrectPasswordException extends AuthenticationException {

    public InCorrectPasswordException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public InCorrectPasswordException(String msg) {
        super(msg);
    }
}
