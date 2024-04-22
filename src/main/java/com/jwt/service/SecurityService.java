package com.jwt.service;

import com.jwt.model.UserModel;

public interface SecurityService {
    public UserModel registerUser(UserModel userModel);
    public String getToken(UserModel userModel);
}
