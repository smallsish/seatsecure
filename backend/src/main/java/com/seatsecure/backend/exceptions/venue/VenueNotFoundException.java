package com.seatsecure.backend.exceptions.venue;

public class VenueNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public VenueNotFoundException(Long id) {
        super("Could not find venue " + id);
    }
}
