package com.seatsecure.backend.exceptions.creation;

public class EventCreationException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public EventCreationException() {
        super("An error has occurred with the creation of the event!");
    }
}
