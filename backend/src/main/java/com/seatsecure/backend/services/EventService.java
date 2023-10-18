package com.seatsecure.backend.services;

import java.util.List;

import com.seatsecure.backend.entities.Event;
import com.seatsecure.backend.entities.Venue;
import com.seatsecure.backend.entities.DTOs.EventVenueDTO;

public interface EventService {
    List<EventVenueDTO> listEvents();

    EventVenueDTO getEventVenueById(Long id);

    Event getEventById(Long id);

    Venue getVenueOfEvent(Long eventId);

    Event addEvent(Event e);

    Event updateEvent(Long id, Event newEventInfo);

    Event setVenueForEvent(Long eventId, Long venueId);

    Event deleteEventById(Long id);

}
