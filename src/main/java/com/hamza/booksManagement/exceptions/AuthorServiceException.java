package com.hamza.booksManagement.exceptions;

public class AuthorServiceException extends RuntimeException {
    public AuthorServiceException(String message) {
        super(message);
    }
}
