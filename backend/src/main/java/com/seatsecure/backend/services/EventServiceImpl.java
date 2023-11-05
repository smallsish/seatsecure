package com.seatsecure.backend.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import com.seatsecure.backend.entities.Category;
import com.seatsecure.backend.entities.Event;
import com.seatsecure.backend.entities.Run;
import com.seatsecure.backend.entities.Venue;
import com.seatsecure.backend.repositories.EventRepository;

@Service
public class EventServiceImpl implements EventService {

    private EventRepository eventRepo;
    private VenueService venueService;

    public EventServiceImpl(VenueService vs, EventRepository er) {
        eventRepo = er;
        venueService = vs;
    }
    
    @Override
    public List<Event> getAll() {
        return eventRepo.findAll();
    }


    @Override
    public Event getEventById(Long id) {
        Optional<Event> e = eventRepo.findById(id);
        if (e.isEmpty())
            return null;

        return e.get();
    }

    @Override
    public Venue getVenueOfEvent(Long eventId) {
        // Check if event exists
        Event e = getEventById(eventId);
        if (e == null)
            return null;

        // Check if venue of event exists
        Venue v = e.getVenue();
        if (v == null)
            return null;

        return v;
    }

    public List<Event> getEventsOfVenue(Long venueId) {
        // Check if venue exists
        Venue v = venueService.getVenueById(venueId);
        if (v == null)
            return null;

        // Get events of that venue
        return eventRepo.findByVenue(v);
    }

    @Override
    public Event addEvent(Event eventInfo) {
        Event e = Event.builder().name(eventInfo.getName())
        .startDate(eventInfo.getStartDate())
        .endDate(eventInfo.getEndDate())
        .venue(null)
        .cats(new ArrayList<Category>())
        .runs(new ArrayList<Run>())
        .build();

        return eventRepo.save(e);
    }

    @Override
    public Event updateEvent(Long id, Event newEventInfo) {
        Optional<Event> event = eventRepo.findById(id);
        if (event.isEmpty())
            return null; // Event not found

        Event e = event.get();
        e.setName(newEventInfo.getName());
        e.setStartDate(newEventInfo.getStartDate());
        e.setEndDate(newEventInfo.getEndDate());

        return eventRepo.save(e);
    }

    @Override
    public Event setVenueForEvent(Long eventId, Long venueId) {
        Event e = getEventById(eventId);
        Venue v = venueService.getVenueById(venueId);
        if (e == null || v == null)
            return null;

        // Update database
        e.setVenue(v);
        return eventRepo.save(e);
    }

    /**
     * Remove a event with the given id
     * Spring Data JPA does not return a value for delete operation
     */
    @Override
    public Event deleteEventById(Long eventId) {
        // Check if event exists
        Event event = getEventById(eventId);
        if (event == null)
            return null;

        // Delete event from database
        eventRepo.deleteById(eventId);

        return event;
    }


}
