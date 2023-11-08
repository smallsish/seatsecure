package com.seatsecure.backend.entities.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Status {
    SUCCESSFUL("Successful"),
    UNSUCCESSFUL("Unsuccessful"),
    PARTIALLYSUCESSFUL("PartiallySuccessful"),
    BIDPLACED("Bidplaced");

    @Getter
    private final String status;
    
}
