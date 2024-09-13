package com.backend.easyturn;

import com.backend.easyturn.exceptions.AppException;
import com.backend.easyturn.exceptions.ErrorDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)
    @ResponseBody
    public ResponseEntity<ErrorDTO> response(AppException e) {
        return ResponseEntity
                .status(e.getStatus())
                .body(new ErrorDTO(e.getMessage()));
    }

    //falta manejo de excepciones para el login

}