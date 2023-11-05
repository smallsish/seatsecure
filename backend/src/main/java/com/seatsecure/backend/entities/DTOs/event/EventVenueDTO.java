package com.seatsecure.backend.entities.DTOs.event;

import com.seatsecure.backend.entities.DTOs.venue.VenueDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EventVenueDTO{
    private EventDTO event;
    private VenueDTO venue;
}
