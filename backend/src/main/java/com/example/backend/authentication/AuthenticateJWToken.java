package com.example.backend.authentication;

import com.example.backend.config.ApiConfig;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AuthenticateJWToken {
    private final ApiConfig apiConfig;

    @Autowired
    public AuthenticateJWToken(ApiConfig apiConfig) {
        this.apiConfig = apiConfig;
    }

    public boolean verifyToken(String token) throws JwtException {
        JWToken jwt = getJWToken(token);
        Long accountId = jwt.getAccountId();

        if (accountId == null) {
            throw new JwtException("invalid jwt token");
        }

        return true;
    }

    public JWToken getJWToken(String token) {
        String issuer = this.apiConfig.getIssuer();
        String passPhrase = this.apiConfig.getPassphrase();
        return JWToken.decode(token.replace("Bearer ", ""), issuer, passPhrase);
    }
}
