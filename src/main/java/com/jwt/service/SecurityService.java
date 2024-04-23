package com.jwt.service;

import com.jwt.exception.UserIsAlreadyRegisteredException;
import com.jwt.model.UserModel;

public interface SecurityService {
    public UserModel registerUser(UserModel userModel) throws UserIsAlreadyRegisteredException;
    public String getToken(UserModel userModel);
}
