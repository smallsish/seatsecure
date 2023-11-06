package com.seatsecure.backend.exceptions.category;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class CatNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public CatNotFoundException(Long id) {
        super("Could not find category " + id);
    }
}
