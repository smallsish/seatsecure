package com.seatsecure.backend.services;

import java.util.List;

import com.seatsecure.backend.entities.Category;
import com.seatsecure.backend.entities.Event;
import com.seatsecure.backend.entities.EventVenueDTO;
import com.seatsecure.backend.entities.Run;
import com.seatsecure.backend.entities.Venue;

public interface EventService {
    List<EventVenueDTO> listEvents();
    EventVenueDTO getEventVenueById(Long id);
    Event getEventById(Long id);
    Event addEvent(Event e);
    Event updateEvent(Long id, Event newEventInfo);
    Event deleteEventById(Long id);

    Venue getVenueOfEvent(Long eventId);
    Event setVenueForEvent(Long eventId, Long venueId);
    Event addNewRunToEvent(Long eventId, Run run);
    Event addNewCatToEvent(Long eventId, Category cat);
    List<Category> getCatsOfEvent(Long id);
    public Event assignCatsToSeats(Long eventId, int startSeatNum, int endSeatNum, Category cat);
    List<Run> getRun(Long eventId);

}
