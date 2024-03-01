package org.example.bcm.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        var body = new ObjectMapper().writeValueAsString(new HashMap<String, Object>() {{
            put("status", HttpStatus.UNAUTHORIZED.value());
            put("error", HttpStatus.UNAUTHORIZED.getReasonPhrase());
            put("message", authException.getMessage());

        }});

        response.getWriter().write(body);
    }
}
