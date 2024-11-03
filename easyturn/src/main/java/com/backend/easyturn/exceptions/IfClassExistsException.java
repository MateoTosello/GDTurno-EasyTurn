package com.backend.easyturn.exceptions;

import org.springframework.http.HttpStatus;

public class IfClassExistsException extends AppException {
//ESTA EXCEPCION SE LANZA CUANDO SE INTENTA CREAR UNA CLASE QUE YA EXISTE
    public IfClassExistsException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
