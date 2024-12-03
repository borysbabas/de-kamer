package com.example.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApiConfig implements WebMvcConfigurer {
    @Value("${jwt.issuer:private company}")
    private String issuer;
    @Value("${jwt.passphrase: This is a very secret information for my private encryption key.}")
    private String passphrase;
    @Value("${jwt.duration-of-validity:1200}")
    private int tokenDurationOfValidity;
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns(
                        "http://localhost:3000", "http://*.hva.nl"
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders(HttpHeaders.AUTHORIZATION,
                        HttpHeaders.CONTENT_TYPE)
                .exposedHeaders(HttpHeaders.AUTHORIZATION,
                        HttpHeaders.CONTENT_TYPE)
                .allowCredentials(true);
    }
public String getIssuer(){
        return  this.issuer;
}
public String getPassphrase(){
        return this.passphrase;
}
public int getTokenDurationOfValidity(){
        return this.tokenDurationOfValidity;
}
public void setIssuer(String issuer){
        this.issuer=issuer;

}
public void setPassphrase(String passphrase) {
        this.passphrase=passphrase;

}
public void setTokenDurationOfValidity(int tokenDurationOfValidity){
        this.tokenDurationOfValidity=tokenDurationOfValidity;
}
}
