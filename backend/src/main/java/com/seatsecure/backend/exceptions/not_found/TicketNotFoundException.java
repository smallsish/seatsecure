package com.seatsecure.backend.exceptions.not_found;

public class TicketNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public TicketNotFoundException(Long id) {
        super("Could not find ticket " + id);
    }
}
