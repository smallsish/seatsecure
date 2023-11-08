package com.seatsecure.backend.exceptions.existence;

public class TicketHasRunException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public TicketHasRunException(Long id) {
        super(String.format("The ticket %d is already associated with an existing run!", id));
    }
}
