package com.seatsecure.backend.exceptions;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import lombok.Getter;

@Getter
public class RegistrationValidationError extends RuntimeException {
    private BindingResult bindingResult;

    public RegistrationValidationError(BindingResult bindingResult, String msg) {
        super(msg);
        this.bindingResult = bindingResult;
    }
}
