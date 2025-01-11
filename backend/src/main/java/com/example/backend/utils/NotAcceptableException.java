package com.example.backend.utils;

/**
 *Custom exception to handle cases where a request is not acceptable.
 * This exception extends RuntimeException and is used to provide more specific error handling in the project.
 */
public class NotAcceptableException extends RuntimeException{
    public NotAcceptableException(String message){
        super(message);

    }
}
