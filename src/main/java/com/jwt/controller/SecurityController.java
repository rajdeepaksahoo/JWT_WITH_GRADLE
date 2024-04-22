package com.jwt.controller;

import com.jwt.model.UserModel;
import com.jwt.service.SecurityService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class SecurityController {
    private SecurityService securityService;
    @PostMapping("/register")
    public UserModel registerUser(@RequestBody UserModel userModel){
        return securityService.registerUser(userModel);
    }
    @PostMapping("/token")
    public String getToken(@RequestBody UserModel userModel){
        return securityService.getToken(userModel);
    }
    @GetMapping
    public String home(){
        return "Home Page";
    }

    @GetMapping("/authorized")
    public String authorized(){
        return "Home Page";
    }
}
