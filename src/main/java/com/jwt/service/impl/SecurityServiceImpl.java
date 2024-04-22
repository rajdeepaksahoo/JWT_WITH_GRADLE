package com.jwt.service.impl;

import com.jwt.model.UserModel;
import com.jwt.repository.SecurityRepository;
import com.jwt.security.jwt.JwtService;
import com.jwt.security.userdetails.CustomUserDetails;
import com.jwt.service.SecurityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class SecurityServiceImpl implements SecurityService {
    private SecurityRepository securityRepository;
    private JwtService jwtService;
    @Override
    public UserModel registerUser(UserModel userModel) {
        Optional<UserModel> byUserName = securityRepository.findByUserName(userModel.getUserName());
        if(byUserName.isEmpty())
            return securityRepository.save(userModel);
        return null;
    }
    @Override
    public String getToken(UserModel userModel){
        return jwtService.generateToken(new CustomUserDetails(userModel));
    }
}
