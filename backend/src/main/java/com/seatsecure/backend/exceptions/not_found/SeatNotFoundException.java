package com.seatsecure.backend.exceptions.not_found;

public class SeatNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public SeatNotFoundException(Long id) {
        super("Could not find seat " + id);
    }
}
