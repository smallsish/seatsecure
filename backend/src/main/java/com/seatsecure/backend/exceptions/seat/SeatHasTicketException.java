package com.seatsecure.backend.exceptions.seat;

public class SeatHasTicketException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public SeatHasTicketException(Long id) {
        super(String.format("The seat %d already has an exception", id));
    }
}
