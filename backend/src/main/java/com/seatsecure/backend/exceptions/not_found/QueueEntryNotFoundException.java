package com.seatsecure.backend.exceptions.not_found;

public class QueueEntryNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public QueueEntryNotFoundException() {
        super("Could not find the entry!");
    }


    public QueueEntryNotFoundException(Long id) {
        super("Could not find entry " + id);
    }
}
