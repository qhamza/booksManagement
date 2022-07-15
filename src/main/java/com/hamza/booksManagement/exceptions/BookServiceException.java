package com.hamza.booksManagement.exceptions;

public class BookServiceException extends RuntimeException{
    public BookServiceException(String message) {
        super(message);
    }
}
