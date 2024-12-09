package com.example.backend.authentication;
import io.jsonwebtoken.*;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import javax.crypto.spec.SecretKeySpec;
public class JWToken {
    public static final String JWT_ATTRIBUTE_NAME ="jwtAttribute" ;
    private static final String JWT_ROLE_CLAIM="role";
    private static final String JWT_IPADDRESS_CLAIM="ipa";
    private static final String JWT_CALLNAME_CLAIM="sub";
    private static final String JWT_ACCOUNTID_CLAIM="id";

    private String callName;
    private Long accountId;
    private String role;

    public JWToken(String callName, Long accountId, String role){
        this.callName=callName;
        this.accountId=accountId;
        this.role=role;
    }
    public String encode(String issuer, String passphrase  , int expiration){
        Key key = getKey(passphrase);

        return Jwts.builder()
                .subject(this.callName)
                .id(this.accountId.toString())
                .issuer(issuer)
                .issuedAt(new Date())
                .expiration(new Date (System.currentTimeMillis()+ expiration*1000L))
                .claim(JWT_ROLE_CLAIM, this.role)
//                .claim(JWT_IPADDRESS_CLAIM, this.ipAddress!=null? this.ipAddress:"1.1.1.1")
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

    }
    private static Key getKey(String passphrase){
        byte[] hmacKey = passphrase.getBytes(StandardCharsets.UTF_8);
//        return new SecretKeySpec(hmacKey, SignatureAlgorithm.HS512.getJcaName());
        return Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }
    public static JWToken decode(String token, String issuer, String passphrase)throws ExpiredJwtException, MalformedJwtException{
        Key key = getKey(passphrase);
        Jws<Claims> jws= Jwts.parser().setSigningKey(key).build().parseClaimsJws(token);
        Claims claims = jws.getBody();

        if(!claims.getIssuer().equals(issuer)){
            throw new MalformedJwtException("invalid issuer");
        }
        JWToken jwToken = new JWToken(claims.getSubject(),Long.valueOf(claims.getId()),claims.get(JWT_ROLE_CLAIM).toString());
        return jwToken;
    }



    public boolean isAdmin() {
        return role != null && role.contains("admin");

    }
}
