package com.jwt.security.entrypoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jwt.exception.InCorrectPasswordException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class JwtAuthenticationEntryPoint implements org.springframework.security.web.AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ObjectMapper objectMapper = new ObjectMapper();
        PrintWriter writer = response.getWriter();
        if(authException instanceof InCorrectPasswordException){
            // Set the HTTP status code to 401 Unauthorized
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            // Set the content type of the response
            response.setContentType("application/json");


            Map<String,Object> responseMap = new HashMap<>();
            responseMap.put("Status","Failed");
            responseMap.put("Reason","Incorrect Password");
            writer.write(objectMapper.writeValueAsString(responseMap));
        }else if(authException instanceof UsernameNotFoundException){
            // Set the HTTP status code to 401 Unauthorized
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            // Set the content type of the response
            response.setContentType("application/json");


            Map<String,Object> responseMap = new HashMap<>();
            responseMap.put("Status","Failed");
            responseMap.put("Reason","User Is Not Registered");
            writer.write(objectMapper.writeValueAsString(responseMap));
        }else{
            // Set the HTTP status code to 401 Unauthorized
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            // Set the content type of the response
            response.setContentType("application/json");

            // Write the JSON object to the response body
            PrintWriter out = writer;
            out.print("Authorization Failed");
            out.flush();
        }
    }
}
