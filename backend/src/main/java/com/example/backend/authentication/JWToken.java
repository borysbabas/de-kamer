package com.example.backend.authentication;

import com.example.backend.config.ApiConfig;
import io.jsonwebtoken.*;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Objects;

/**
 * public class that represents a jwt for users authentication and authorization.
 * It provides methods to encode,decode and validate JWT.
 */
public class JWToken {
    public static final String JWT_ATTRIBUTE_NAME ="jwtAttribute" ;
    private static final String JWT_ROLE_CLAIM="role";

    private final String callName;
    @Getter
    private final Long accountId;
    private final String role;

    /**
     * The constructor that instantiates the actual token
     * @param callName the name of the caller or user.
     * @param accountId the unique id of the account.
     * @param role the role of the user. Used to regulate certain parts of the website.
     */
    public JWToken(String callName, Long accountId, String role){
        this.callName = callName;
        this.accountId = accountId;
        this.role = role;
    }

    /**
     * Method to encode the token into a signed string.
     * @param issuer the entity issuing the token.
     * @param passphrase a secret to sign the token
     * @param expiration the time when the token expires
     * @return A signed jwt.
     */
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

    /**
     * Generates a signed key using the SHA-512 algorithm.
     * Note: the use of the byte array is essential as it doesn't work without.
     * @param passphrase the secret used to obtain the key.
     * @return
     */
    private static Key getKey(String passphrase) {
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            byte[] hashedKey = digest.digest(passphrase.getBytes(StandardCharsets.UTF_8));
            return Keys.hmacShaKeyFor(hashedKey);


        }catch (NoSuchAlgorithmException e){
            throw new RuntimeException("error generatingkey",e);
        }
    }

    /**
     * Decodes a JWT string into a JWtoken object.
     * @param token the jwt string to decode
     * @param issuer the entity that issued the call.
     * @param passphrase the secretkey used to verify the tokens validity
     * @return a new jwt token that has been decoded to authenticate the token.
     * @throws ExpiredJwtException exception to throw when the token is expired
     * @throws MalformedJwtException exception to throw when the token has been altered.
     */
    public static JWToken decode(String token, String issuer, String passphrase)throws ExpiredJwtException, MalformedJwtException{
        Key key = getKey(passphrase);

        Jws<Claims> jws= Jwts.parser().setSigningKey(key).build().parseClaimsJws(token);
        Claims claims = jws.getBody();

        if(!claims.getIssuer().equals(issuer)){
            throw new MalformedJwtException("invalid issuer");
        }

        return new JWToken(claims.getSubject(),Long.valueOf(claims.getId()),claims.get(JWT_ROLE_CLAIM).toString());
    }

    /**
     * helper method to check whether entity that issued the token has the role admin.
     * @return the role of the token.
     */
    public boolean isAdmin() {
        return role != null && role.contains("admin");

    }
}
