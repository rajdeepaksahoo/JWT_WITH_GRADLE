package com.jwt.security.userdetails;

import com.jwt.model.UserModel;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {
    private UserModel userModel;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userModel.getRoles().stream().map(customRoles -> new SimpleGrantedAuthority(customRoles.getRoleName())).toList();
    }

    @Override
    public String getPassword() {
        return userModel.getPassword();
    }

    @Override
    public String getUsername() {
        return userModel.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return userModel.getIsActive();
    }

    @Override
    public boolean isAccountNonLocked() {
        return userModel.getIsActive();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return userModel.getIsActive();
    }

    @Override
    public boolean isEnabled() {
        return userModel.getIsActive();
    }
}
