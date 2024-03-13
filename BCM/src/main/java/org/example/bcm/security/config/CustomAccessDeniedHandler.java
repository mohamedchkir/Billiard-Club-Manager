package org.example.bcm.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setStatus(HttpStatus.FORBIDDEN.value());

        var body = new ObjectMapper().writeValueAsString(
                new HashMap<String, Object>() {{
                    put("status", HttpStatus.FORBIDDEN.value());
                    put("error", HttpStatus.FORBIDDEN.getReasonPhrase());
                    put("message", accessDeniedException.getMessage());

                }}
        );

        response.getWriter().write(body);
    }
}

