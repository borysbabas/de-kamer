package com.example.backend.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Getter
@Configuration
public class ApiConfig implements WebMvcConfigurer {
    @Value("${jwt.issuer:private company}")
    private String issuer;

    @Value("${jwt.passphrase: This is a very secret information for my private encryption key.}")
    private String passphrase;

    @Value("${jwt.duration-of-validity:1200}")
    private int tokenDurationOfValidity;

    @Getter
    private final static String[] allowedOrigins = {
            "http://localhost:3000", "http://*.hva.nl", "https://de-kamer.eu", "https://www.de-kamer.eu"
    };

    @Getter
    private final static String productionOrigin = "https://de-kamer.eu";

    @Getter
    private final static String developmentOrigin = "http://localhost:3000";

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(allowedOrigins).allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders(HttpHeaders.AUTHORIZATION,
                        HttpHeaders.CONTENT_TYPE)
                .exposedHeaders(HttpHeaders.AUTHORIZATION,
                        HttpHeaders.CONTENT_TYPE)
                .allowCredentials(true);
    }

}
