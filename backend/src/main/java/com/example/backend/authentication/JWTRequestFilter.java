package com.example.backend.authentication;

import com.example.backend.config.ApiConfig;
import com.example.backend.entities.Account;
import com.example.backend.services.AccountService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {

    private final AuthenticateJWToken authenticateJWToken;
    private final AccountService accountService;
    private final String[] userApiPaths = {
            "/account",
    };
    private final String[] adminApiPaths = {
            "/admin",
    };
    private final String[] preflightHeaders = {
            HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD,
            HttpHeaders.ORIGIN,
    };

    public JWTRequestFilter(AuthenticateJWToken authenticateJWToken, AccountService accountService) {
        this.authenticateJWToken = authenticateJWToken;
        this.accountService = accountService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String requestURI = request.getRequestURI();

        if (Arrays.stream(userApiPaths).anyMatch(requestURI::startsWith)) {
            verifyAccountAccess(request, response);
        }
        if (Arrays.stream(adminApiPaths).anyMatch(requestURI::startsWith)) {
            verifyAdminAccess(request, response);
        }

        chain.doFilter(request, response);
    }

    /**
     * Verify if the account has access to the requested resource
     * @param request the request to check
     * @param response the response to send
     */
    private void verifyAccountAccess(HttpServletRequest request, HttpServletResponse response) {
        String jwt = request.getHeader(HttpHeaders.AUTHORIZATION);

        // If no JWT is provided, it might be a preflight request
        if (jwt == null) {
            if (isPreflightRequest(request)) {
                response.setStatus(HttpServletResponse.SC_OK);
                return;
            }
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        try {
            JWToken jwtToken = authenticateJWToken.getJWToken(jwt);

            if (jwtToken == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            Account account = accountService.findById(jwtToken.getAccountId());

            if (account == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            request.setAttribute(ApiConfig.ACCOUNT_HEADER, account);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

    }

    /**
     * Verify if the account has admin access to the requested resource
     *
     * @param request the request to check
     * @param response the response to send
     */
    private void verifyAdminAccess(HttpServletRequest request, HttpServletResponse response) {
        // An admin should have access to a valid account
        verifyAccountAccess(request, response);

        Account account = (Account) request.getAttribute(ApiConfig.ACCOUNT_HEADER);

        if (account == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        if (account.getRole().equals("admin")) {
            request.setAttribute(ApiConfig.ACCOUNT_HEADER, account);
            response.setStatus(HttpServletResponse.SC_OK);
        }

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    /**
     * Check if the request is a preflight request
     * If a preflight request is made, the server should respond with a 200 status code
     * @param request the request to check
     * @return true if the request is a preflight request, false otherwise
     */
    private boolean isPreflightRequest(HttpServletRequest request) {
        if (!request.getMethod().equalsIgnoreCase("OPTIONS")) {
            return false;
        }
        for (String header : preflightHeaders) {
            if (request.getHeader(header) == null) {
                return false;
            }
        }
        return true;
    }
}
