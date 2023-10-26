package com.seatsecure.backend.entities.DTOs;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class VenueEventsDTO {
    // Given a venue, give a list of its events

    VenueDTO venue;
    List<EventDTO> events;
}
