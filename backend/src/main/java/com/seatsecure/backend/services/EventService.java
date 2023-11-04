package com.seatsecure.backend.services;

import java.util.List;

import com.seatsecure.backend.entities.Event;
import com.seatsecure.backend.entities.Venue;
import com.seatsecure.backend.entities.DTOs.EventDTO;
import com.seatsecure.backend.entities.DTOs.EventDetailsDTO;
import com.seatsecure.backend.entities.DTOs.VenueEventsDTO;

public interface EventService {
    // DTO methods

    // Service methods

    List<Event> getAll();

    Event getEventById(Long id);

    Venue getVenueOfEvent(Long eventId);

    List<Event> getEventsOfVenue(Long venueId);

    Event addEvent(Event e);

    Event updateEvent(Long id, Event newEventInfo);

    Event setVenueForEvent(Long eventId, Long venueId);

    Event deleteEventById(Long id);

}
