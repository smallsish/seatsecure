package com.seatsecure.backend.exceptions.run;


public class RunNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public RunNotFoundException(Long id) {
        super("Could not find the run " + id);
    }
}
