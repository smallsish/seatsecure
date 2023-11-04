package com.seatsecure.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CurrentUserException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public CurrentUserException() {
        super("The current user cannot be determined! Please try again later!");
    }
}
