package com.seatsecure.backend.exceptions.not_found;

public class CatNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public CatNotFoundException(Long id) {
        super("Could not find category " + id);
    }
}
