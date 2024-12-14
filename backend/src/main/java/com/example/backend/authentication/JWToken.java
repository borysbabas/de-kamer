package com.example.backend.authentication;

import io.jsonwebtoken.*;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

public class JWToken {
    public static final String JWT_ATTRIBUTE_NAME ="jwtAttribute" ;
    private static final String JWT_ROLE_CLAIM="role";

    private final String callName;
    private final Long accountId;
    private final String role;

    public JWToken(String callName, Long accountId, String role){
        this.callName = callName;
        this.accountId = accountId;
        this.role = role;
    }

    public String encode(String issuer, String passphrase, int expiration){
        Key key = getKey(passphrase);

        return Jwts.builder()
                .subject(this.callName)
                .id(this.accountId.toString())
                .issuer(issuer)
                .issuedAt(new Date())
                .expiration(new Date (System.currentTimeMillis()+ expiration*1000L))
                .claim(JWT_ROLE_CLAIM, this.role)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

    }
    private static Key getKey(String passphrase){
        byte[] hmacKey = passphrase.getBytes(StandardCharsets.UTF_8);
        return Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }

    public static JWToken decode(String token, String issuer, String passphrase)throws ExpiredJwtException, MalformedJwtException{
        Key key = getKey(passphrase);
        Jws<Claims> jws= Jwts.parser().setSigningKey(key).build().parseClaimsJws(token);
        Claims claims = jws.getBody();

        if(!claims.getIssuer().equals(issuer)){
            throw new MalformedJwtException("invalid issuer");
        }
        return new JWToken(claims.getSubject(),Long.valueOf(claims.getId()),claims.get(JWT_ROLE_CLAIM).toString());
    }

    public boolean isAdmin() {
        return role != null && role.contains("admin");

    }
}
