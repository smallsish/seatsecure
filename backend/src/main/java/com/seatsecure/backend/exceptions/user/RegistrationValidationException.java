package com.seatsecure.backend.exceptions.user;

import org.springframework.validation.BindingResult;

import lombok.Getter;

@Getter
public class RegistrationValidationException extends RuntimeException {
    private BindingResult bindingResult;

    public RegistrationValidationException(BindingResult bindingResult, String msg) {
        super(msg);
        this.bindingResult = bindingResult;
    }
}
