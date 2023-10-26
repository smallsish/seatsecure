package com.seatsecure.backend.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.seatsecure.backend.entities.Event;
import com.seatsecure.backend.entities.Venue;
import com.seatsecure.backend.entities.DTOs.EventDTO;
import com.seatsecure.backend.entities.DTOs.EventDetailsDTO;
import com.seatsecure.backend.entities.DTOs.VenueEventsDTO;
import com.seatsecure.backend.entities.DTOs.mappers.EventDetailsDTOmapper;
import com.seatsecure.backend.entities.DTOs.mappers.VenueEventsDTOmapper;
import com.seatsecure.backend.repositories.EventRepository;

import lombok.NoArgsConstructor;

@Service
public class EventServiceImpl implements EventService {

    private EventRepository eventRepo;
    private VenueService vs;
    private EventDetailsDTOmapper eventDetailsDTOmapper;

    public EventServiceImpl(VenueService vs, EventRepository eventRepo, EventDetailsDTOmapper evDTO) {
        this.eventRepo = eventRepo;
        this.vs = vs;
        this.eventDetailsDTOmapper = evDTO;
    }

    // DTO methods
    @Override
    public List<EventDetailsDTO> listEventDetailsDTOs() {
        return eventRepo.findAll().stream().map(eventDetailsDTOmapper).collect(Collectors.toList());
    }

    @Override
    public EventDetailsDTO getEventDetailsDTOById(Long id) {
        // Check if event exists
        Event event = getEventById(id);
        if (event == null) return null;

        return eventDetailsDTOmapper.apply(event);

    }

    @Override
    public VenueEventsDTO listEventDTOsOfVenue(Long id) {
        Venue venue = vs.getVenueById(id);
        if (venue == null) return null;

        return new VenueEventsDTOmapper().apply(venue);
    }

    // @Override
    // public EventVenueDTO getEventVenueById(Long eventId) {
    //     Optional<Event> e = eventRepo.findById(eventId);
    //     if (e.isEmpty())
    //         return null;

    //     return e.map(eventVenueDTOmapper).get();
    // }

    // Service methods
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
        Venue v = vs.getVenueById(venueId);
        if (v == null)
            return null;

        // Get events of that venue
        return eventRepo.findByVenue(v);
    }

    @Override
    public Event addEvent(Event u) {
        return eventRepo.save(u);
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
        eventRepo.save(e);

        return e;
    }

    @Override
    public Event setVenueForEvent(Long eventId, Long venueId) {
        Event e = getEventById(eventId);
        Venue v = vs.getVenueById(venueId);
        if (e == null || v == null)
            return null;

        // Update database
        e.setVenue(v);
        eventRepo.save(e);

        return e;
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

        // Get venue of event
        // Venue venue = getVenueOfEvent(eventId);
        // if (venue == null)
        // return null;

        // Delete event from database
        eventRepo.deleteById(eventId);

        return event;
    }


}
