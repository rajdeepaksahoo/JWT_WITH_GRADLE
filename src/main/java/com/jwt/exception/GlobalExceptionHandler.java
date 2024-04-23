package com.jwt.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserIsAlreadyRegisteredException.class)
    public Map<String,Object> userAlreadyRegistered(UserIsAlreadyRegisteredException exception){
        Map<String,Object> response = new HashMap<>();
        response.put("status","Failed");
        response.put("reason",exception.getMessage());
        return response;
    }
}
