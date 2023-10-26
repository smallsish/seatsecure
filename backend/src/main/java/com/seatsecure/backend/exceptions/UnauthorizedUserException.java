package com.seatsecure.backend.exceptions;

public class UnauthorizedUserException extends RuntimeException {
    public UnauthorizedUserException() {
        super("You are not authorized to view this user's profile!");
    }
}
