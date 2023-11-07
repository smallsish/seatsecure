package com.seatsecure.backend.exceptions.others;

public class UnauthorizedUserException extends RuntimeException {
    public UnauthorizedUserException() {
        super("You are not authorized to do this!");
    }
}
