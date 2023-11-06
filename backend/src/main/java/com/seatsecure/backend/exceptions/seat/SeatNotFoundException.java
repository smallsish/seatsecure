package com.seatsecure.backend.exceptions.seat;

public class SeatNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public SeatNotFoundException(Long id) {
        super("Could not find seat " + id);
    }
}
