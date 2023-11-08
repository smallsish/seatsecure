package com.seatsecure.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class BiddingNotCloseException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public BiddingNotCloseException() {
        super("Bidding window has not close!");
    }
}
