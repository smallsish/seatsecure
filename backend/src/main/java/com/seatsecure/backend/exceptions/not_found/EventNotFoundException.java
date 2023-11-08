package com.seatsecure.backend.exceptions.not_found;

public class EventNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public EventNotFoundException(Long id) {
        super("Could not find event " + id);
    }
}
