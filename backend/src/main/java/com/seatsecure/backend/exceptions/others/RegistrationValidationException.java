package com.seatsecure.backend.exceptions.others;

import org.springframework.validation.BindingResult;

import lombok.Getter;

@Getter
public class RegistrationValidationException extends RuntimeException {
    private BindingResult bindingResult;

    public RegistrationValidationException(BindingResult bindingResult) {
        super("Validation error!");
        this.bindingResult = bindingResult;
    }
}
