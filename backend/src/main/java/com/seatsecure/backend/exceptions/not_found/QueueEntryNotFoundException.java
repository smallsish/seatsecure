package com.seatsecure.backend.exceptions.not_found;

public class QueueEntryNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public QueueEntryNotFoundException(Long id) {
        super("Could not find event " + id);
    }
}
