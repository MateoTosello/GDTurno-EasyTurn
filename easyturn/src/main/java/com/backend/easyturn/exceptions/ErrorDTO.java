package com.backend.easyturn.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorDTO {
    private String message;
    private String status;

    public ErrorDTO(String message, String status) {
        this.message = message;
        this.status = status;
    }
}
