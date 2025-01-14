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

/**
 * A general filter that can be used to authenticate every HTTP request.
 */
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
    private final String[] bannedApiPaths = {
            "/admin",
            "/account/posts/add",
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

        if (isPreflightRequest(request)) {
            response.setStatus(HttpServletResponse.SC_OK);
            chain.doFilter(request, response);
            return;
        }

        if (Arrays.stream(userApiPaths).anyMatch(requestURI::startsWith)) {
            if (!verifyAccountAccess(request, response)) {
                return;
            }
        }
        if (Arrays.stream(adminApiPaths).anyMatch(requestURI::startsWith)) {
            if (!verifyAdminAccess(request, response)) {
                return;
            }
        }

        chain.doFilter(request, response);
    }

    /**
     * Verify if the account has access to the requested resource
     * @param request the request to check
     * @param response the response to send
     */
    private boolean verifyAccountAccess(HttpServletRequest request, HttpServletResponse response) {
        String jwt = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (jwt == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        try {
            JWToken jwtToken = authenticateJWToken.getJWToken(jwt);

            if (jwtToken == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }

            Account account = accountService.findById(jwtToken.getAccountId());

            if (account == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }

            if (account.isBanned() && Arrays.stream(bannedApiPaths).anyMatch(request.getRequestURI()::startsWith)) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return false;
            }

            request.setAttribute(ApiConfig.ACCOUNT_HEADER, account);
            return true;
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
    }

    /**
     * Verify if the account has admin access to the requested resource
     *
     * @param request the request to check
     * @param response the response to send
     */
    private boolean verifyAdminAccess(HttpServletRequest request, HttpServletResponse response) {
        if (!verifyAccountAccess(request, response)) {
            return false;
        }

        Account account = (Account) request.getAttribute(ApiConfig.ACCOUNT_HEADER);

        if (account == null || !account.getRole().equals("admin")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        return true;
    }

    /**
     * Check if the request is a preflight request
     * If a preflight request is made, the server should respond with a 200 status code
     * @param request the request to check
     * @return true if the request is a preflight request, false otherwise
     */
    private boolean isPreflightRequest(HttpServletRequest request) {
        if (request.getHeader(HttpHeaders.AUTHORIZATION) != null) {
            return false;
        }
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
