package com.seatsecure.backend.exceptions.existence;

public class TicketHasOwnerException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public TicketHasOwnerException(Long id) {
        super(String.format("The ticket %d already has an owner!", id));
    }
}
