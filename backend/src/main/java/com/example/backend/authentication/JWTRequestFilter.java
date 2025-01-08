package com.example.backend.authentication;

import com.example.backend.config.ApiConfig;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        URL origin = new URL(request.getRequestURL().toString());
        String requestOrigin = request.getHeader("Origin");
        System.out.println(requestOrigin);

        System.out.println(origin.getHost());

        String originString = switch (origin.getHost()) {
            case "localhost" -> ApiConfig.getDevelopmentOrigin();
            case "127.0.0.1" ->
                    origin.getProtocol() + "://" + origin.getHost() + (origin.getPort() > -1 ? ":" + origin.getPort() : "");
            default -> ApiConfig.getProductionOrigin();
        };

        boolean allowedOrigin = Arrays.asList(ApiConfig.getAllowedOrigins()).contains(originString);

        if (allowedOrigin) {
            response.setHeader("Access-Control-Allow-Origin", originString);
            response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Max-Age", "1800");
            response.setStatus(HttpServletResponse.SC_OK);
        }

        chain.doFilter(request, response);
    }
}
