package com.seatsecure.backend.exceptions.creation;

public class VenueCreationException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public VenueCreationException() {
        super("An error has occurred with the creation of the event!");
    }
}
