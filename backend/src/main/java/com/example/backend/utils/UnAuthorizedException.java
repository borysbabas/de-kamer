package com.example.backend.utils;

/**
 * Custom exception that is used to help better understand when a client is unauthorized in certain cases.
 */
public class UnAuthorizedException extends RuntimeException {
    public UnAuthorizedException(String message) {
        super(message);
    }
}
