package com.backend.easyturn.exceptions;

import org.springframework.http.HttpStatus;

public class RequestIncompleteException extends AppException {
    public RequestIncompleteException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
