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
    ApiConfig apiConfig = new ApiConfig();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String encryptedToken= request.getHeader(HttpHeaders.AUTHORIZATION);

        if(encryptedToken == null){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"No token provided");
            return;
        }
        try{
            JWToken jwToken = JWToken.decode(encryptedToken.replace("Bearer ", ""), this.apiConfig.getIssuer(), this.apiConfig.getPassphrase());
            request.setAttribute(JWToken.JWT_ATTRIBUTE_NAME, jwToken);
            chain.doFilter(request,response);

        }catch(RuntimeException e){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            e.getMessage();
        }
    }
}
