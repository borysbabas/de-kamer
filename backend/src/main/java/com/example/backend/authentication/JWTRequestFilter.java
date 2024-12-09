package com.example.backend.authentication;

import com.example.backend.config.ApiConfig;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JWTRequestFilter extends OncePerRequestFilter {
    private final ApiConfig apiConfig = new ApiConfig();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String encryptedToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        String requestURI = request.getRequestURI();

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
            response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Max-Age", "1800");
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        if (requestURI.equals("/authentication/login") || requestURI.equals("/authentication/register")) {
            chain.doFilter(request, response);
            return;
        }

        if (encryptedToken == null || !encryptedToken.startsWith("Bearer ")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No token provided");
            return;
        }

        try {
            String token = encryptedToken.replace("Bearer ", "");
            JWToken jwToken = JWToken.decode(token, this.apiConfig.getIssuer(), this.apiConfig.getPassphrase());
            request.setAttribute(JWToken.JWT_ATTRIBUTE_NAME, jwToken);
        } catch (RuntimeException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
            return;
        }
        chain.doFilter(request, response);
    }
}
