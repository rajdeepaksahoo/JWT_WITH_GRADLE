package com.jwt.security.filter;

import com.jwt.repository.SecurityRepository;
import com.jwt.security.authentication.CustomAuthentication;
import com.jwt.security.jwt.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@AllArgsConstructor
@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter {
    private JwtService jwtService;
    private SecurityRepository securityRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");

        if(null!=authorizationHeader &&
                (authorizationHeader.startsWith("Bearer")|| authorizationHeader.startsWith("bearer"))
        ){
            authorizationHeader=authorizationHeader.substring(7);

            boolean isTokenValid = jwtService.isTokenValid(authorizationHeader);

            if(isTokenValid){
                Authentication authentication =
                        new CustomAuthentication(securityRepository.findByUserName(jwtService.getUserNameFromToken(authorizationHeader)).get(),true);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }else {
                log.debug("Token is not valid !!!");
            }

        }else {
            log.debug("Invalid Token");
        }
        filterChain.doFilter(request,response);
    }
}
