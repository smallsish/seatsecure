package com.seatsecure.backend.exceptions.queueEntry;

public class QueueEntryNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public QueueEntryNotFoundException(Long id) {
        super("Could not find event " + id);
    }
}
