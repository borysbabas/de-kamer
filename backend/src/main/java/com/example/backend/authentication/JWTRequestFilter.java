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

/**
 * A general filter that can be used to authenticate every HTTP request.
 */
@Component
public class JWTRequestFilter extends OncePerRequestFilter {
    /**
     *
     * @param request the incoming HTTP request.
     * @param response the HTTP response to be sent back.
     * @param chain the filter chain.
     * @throws ServletException is thrown when there is an error during filtering.
     * @throws IOException is thrown when there is an I/O error during filtering.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
// get the url of the incoming request
        URL origin = new URL(request.getRequestURL().toString());
        String requestOrigin = request.getHeader("Origin");
        System.out.println(requestOrigin);

        System.out.println(origin.getHost());
//determine the origin to be used for CORS based on the request's host.
        String originString = switch (origin.getHost()) {
            case "localhost" -> ApiConfig.getDevelopmentOrigin();
            case "127.0.0.1" ->
                    origin.getProtocol() + "://" + origin.getHost() + (origin.getPort() > -1 ? ":" + origin.getPort() : "");
            default -> ApiConfig.getProductionOrigin();
        };

        boolean allowedOrigin = Arrays.asList(ApiConfig.getAllowedOrigins()).contains(originString);
//if the origins is allowed set the corresponding headers.
        if (allowedOrigin) {
            response.setHeader("Access-Control-Allow-Origin", originString);
            response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Max-Age", "1800");
            response.setStatus(HttpServletResponse.SC_OK);
        }
// if that is not the case continue with the request as normals since the request doesn't require authentication
        chain.doFilter(request, response);
    }
}
