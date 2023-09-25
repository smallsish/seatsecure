package com.seatsecure.backend.services;

import java.util.List;

import com.seatsecure.backend.entities.Event;
import com.seatsecure.backend.entities.EventVenueDTO;

public interface EventService {
    List<EventVenueDTO> listEvents();
    EventVenueDTO getEventVenueById(Long id);
    Event getEventById(Long id);
    Event addEvent(Event e);
    Event updateEvent(Long id, Event newEventInfo);
    Event deleteEventById(Long id);
}
