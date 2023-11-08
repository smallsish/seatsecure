package com.seatsecure.backend.entities.DTOs.simple;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor

public class VenueDTO {
    private Long id;
    private String venueName;
    private String address;
}
