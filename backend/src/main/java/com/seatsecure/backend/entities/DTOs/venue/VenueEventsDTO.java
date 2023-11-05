package com.seatsecure.backend.entities.DTOs.venue;

import java.util.List;

import com.seatsecure.backend.entities.DTOs.event.EventDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class VenueEventsDTO {
    // Given a venue, give a list of its events

    VenueDTO venue;
    List<EventDTO> events;
}
