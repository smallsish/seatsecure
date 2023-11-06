package com.seatsecure.backend.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import com.seatsecure.backend.entities.Category;
import com.seatsecure.backend.entities.Event;
import com.seatsecure.backend.entities.Run;
import com.seatsecure.backend.entities.Venue;
import com.seatsecure.backend.exceptions.event.EventNotFoundException;
import com.seatsecure.backend.exceptions.venue.NullVenueException;
import com.seatsecure.backend.exceptions.venue.VenueNotFoundException;
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

    /**
     * Get the Event with the specified id
     * @param eventId
     * @return The Event with the specified id
     * @throws EventNotFoundException If the Event cannot be found
     */
    @Override
    public Event getEventById(Long eventId) {
        Optional<Event> e = eventRepo.findById(eventId);
        if (e.isEmpty()) {
            throw new EventNotFoundException(eventId);
        }

        return e.get();
    }

    /**
     * Get the Venue where an Event is hosted
     * @param eventId
     * @return The Venue of the Event with the specified id
     * @throws EventNotFoundException If the Event cannot be found
     * @throws NullVenueException     If the Event does not have a Venue
     */
    @Override
    public Venue getVenueOfEvent(Long eventId) {
        // Check if event exists
        Event e = getEventById(eventId);

        // Check if venue of event exists
        Venue v = e.getVenue();
        if (v == null) {
            throw new NullVenueException();
        }

        return v;
    }

    /**
     * Get the Events hosted at a Venue
     * @param eventId
     * @return A list of the Events of a Venue with the specified id
     * @throws VenueNotFoundException If the Venue cannot be found
     */
    public List<Event> getEventsOfVenue(Long venueId) {
        // Check if venue exists
        Venue v = venueService.getVenueById(venueId);

        // Get events of that venue
        return eventRepo.findByVenue(v);
    }

    /**
     * Add a new Event
     * @param eventInfo
     * @return The newly added Event
     */
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

    /**
     * Update an Event with new info (name, startDate, endDate)
     * @param eventId
     * @return The updated Event
     * @throws EventNotFoundException If the Event cannot be found
     */
    @Override
    public Event updateEvent(Long eventId, Event newEventInfo) {
        Event e = getEventById(eventId);

        e.setName(newEventInfo.getName());
        e.setStartDate(newEventInfo.getStartDate());
        e.setEndDate(newEventInfo.getEndDate());

        return eventRepo.save(e);
    }

    /**
     * Update the Venue of an Event
     * @param eventId
     * @param venueId
     * @return The Event with the updated Venue
     * @throws EventNotFoundException If the Event cannot be found
     * @throws VenueNotFoundException If the Venue cannot be found
     */
    @Override
    public Event setVenueForEvent(Long eventId, Long venueId) {
        Event e = getEventById(eventId);
        Venue v = venueService.getVenueById(venueId);

        // Update database
        e.setVenue(v);
        return eventRepo.save(e);
    }

    /**
     * Delete an Event
     * @param eventId
     * @return The Event that is deleted
     * @throws EventNotFoundException If the Event cannot be found
     */
    @Override
    public Event deleteEventById(Long eventId) {
        // Check if event exists
        Event event = getEventById(eventId);

        // Delete event from database
        eventRepo.deleteById(eventId);

        return event;
    }

}
