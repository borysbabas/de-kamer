package com.example.backend.utils;

/**
 * Custom exception that helps identify the current problem in the code base
 * or more specific when a resource such as an already deleted entity cannot be found.
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
