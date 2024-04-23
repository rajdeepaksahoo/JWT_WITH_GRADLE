package com.jwt.service.impl;

import com.jwt.exception.InCorrectPasswordException;
import com.jwt.exception.UserIsAlreadyRegisteredException;
import com.jwt.model.UserModel;
import com.jwt.repository.SecurityRepository;
import com.jwt.security.jwt.JwtService;
import com.jwt.security.userdetails.CustomUserDetails;
import com.jwt.service.SecurityService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class SecurityServiceImpl implements SecurityService {
    private SecurityRepository securityRepository;
    private JwtService jwtService;
    private PasswordEncoder passwordEncoder;
    @Override
    public UserModel registerUser(UserModel userModel) throws UserIsAlreadyRegisteredException {
        Optional<UserModel> byUserName = securityRepository.findByUserName(userModel.getUserName());
        if(byUserName.isEmpty()) {
            userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
            return securityRepository.save(userModel);
        }
        else throw new UserIsAlreadyRegisteredException("User Is Already Registered...");
    }
    @Override
    public String getToken(UserModel userModel){
        String password = passwordEncoder.encode(userModel.getPassword());
        Optional<UserModel> byUserName = securityRepository.findByUserName(userModel.getUserName());
        if(byUserName.isPresent()){
            if(passwordEncoder.matches(userModel.getPassword(),byUserName.get().getPassword())){
                return jwtService.generateToken(new CustomUserDetails(userModel));
            }else {
                throw new InCorrectPasswordException("Incorrect Password...");
            }
        }else{
            throw new UsernameNotFoundException("User Is Not registered...");
        }
    }
}
