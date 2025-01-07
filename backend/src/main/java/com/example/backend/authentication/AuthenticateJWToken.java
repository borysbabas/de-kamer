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

    public boolean verifyToken(String token, Long id) throws JwtException {
        String issuer = this.apiConfig.getIssuer();
        String passPhrase = this.apiConfig.getPassphrase();
        JWToken jwt = JWToken.decode(token.replace("Bearer ", ""), issuer, passPhrase);
        Long accountId = jwt.getAccountId();

        System.out.println(this.apiConfig.getPassphrase());

        if (accountId == null) {
            throw new JwtException("invalid jwt token");
        }

        if (Objects.equals(accountId, id)) {
            return true;
        }

        throw new MalformedJwtException("token has been malformed");
    }
}
