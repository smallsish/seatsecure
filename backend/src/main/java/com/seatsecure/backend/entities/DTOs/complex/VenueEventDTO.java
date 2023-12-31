package com.seatsecure.backend.entities.DTOs.complex;

import com.seatsecure.backend.entities.DTOs.simple.EventDTO;
import com.seatsecure.backend.entities.DTOs.simple.VenueDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class VenueEventDTO {
    // Given a venue, give a list of its events

    VenueDTO venue;
    EventDTO event;
}
