package com.backend.easyturn.exceptions;

import org.springframework.http.HttpStatus;

public class RequestIncorrectLogin extends AppException {
    public RequestIncorrectLogin(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
