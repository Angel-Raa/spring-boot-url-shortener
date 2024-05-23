package com.github.angel.raa.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.angel.raa.dto.AuthenticationErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    private static final  String MESSAGE_NOT_AUTHENTICATED = "no authentication credentials found please log in to access this resource";
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        AuthenticationErrorResponse error = new AuthenticationErrorResponse();
        error.setMessage(MESSAGE_NOT_AUTHENTICATED);
        error.setUrl(request.getRequestURL().toString());
        error.setMethod(request.getMethod());
        error.setTimestamp(LocalDateTime.now());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String json = mapper.writeValueAsString(error);
        response.getWriter().write(json);

    }
}
