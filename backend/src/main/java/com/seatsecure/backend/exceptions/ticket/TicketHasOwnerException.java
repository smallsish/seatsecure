package com.seatsecure.backend.exceptions.ticket;

public class TicketHasOwnerException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public TicketHasOwnerException(Long id) {
        super(String.format("The ticket %d cannot be deleted as it already has an owner!", id));
    }
}
