package com.seatsecure.backend.services;

import java.util.List;

import com.seatsecure.backend.entities.Event;

public interface EventService {
    List<Event> listEvents();
    Event getEventById(Long id);
    Event addEvent(Event e);
    Event updateEvent(Long id, Event newEventInfo);
    Event deleteEventById(Long id);
}
