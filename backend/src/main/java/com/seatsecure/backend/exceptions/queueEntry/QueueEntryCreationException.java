package com.seatsecure.backend.exceptions.queueEntry;

import com.seatsecure.backend.entities.Category;
import com.seatsecure.backend.entities.Run;
import com.seatsecure.backend.entities.User;

public class QueueEntryCreationException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public QueueEntryCreationException(User user, int numOfSeats, Category cat, Run run) {
        super("Could create queue entry " + user + ", numOfSeats: " + numOfSeats + ", Category: " + cat + ", Run: " + run.getName());
    }
}
