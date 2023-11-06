package com.seatsecure.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RunNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public RunNotFoundException(Long id) {
        super("Could not find the run " + id);
    }
}
