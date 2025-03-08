package com.backend.easyturn;

import com.backend.easyturn.exceptions.AppException;
import com.backend.easyturn.exceptions.ErrorDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler extends Throwable {

    @ExceptionHandler(AppException.class)
    @ResponseBody
    @ResponseStatus
    public ResponseEntity<ErrorDTO> response(AppException e) {
        return ResponseEntity
                .status(e.getStatus())
                .body(new ErrorDTO(e.getMessage(), e.getStatus().toString()));
    }

}
