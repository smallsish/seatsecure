package com.seatsecure.backend.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.seatsecure.backend.entities.Category;
import com.seatsecure.backend.entities.Event;
import com.seatsecure.backend.entities.EventVenueDTO;
import com.seatsecure.backend.entities.EventVenueDTOmapper;
import com.seatsecure.backend.entities.Run;
import com.seatsecure.backend.entities.Seat;
import com.seatsecure.backend.entities.Venue;
import com.seatsecure.backend.repositories.CatRepository;
import com.seatsecure.backend.repositories.EventRepository;
import com.seatsecure.backend.repositories.RunRepository;

@Service
public class EventServiceImpl implements EventService {

    private EventRepository eventRepo;
    private CatRepository catRepo;
    private RunRepository runRepo;
    private VenueService vs;
    private EventVenueDTOmapper eventVenueDTOmapper;

    public EventServiceImpl(EventRepository eventRepo, CatRepository catRepo, RunRepository runRepo, VenueService vs, EventVenueDTOmapper evDTO) {
        this.eventRepo = eventRepo;
        this.catRepo = catRepo;
        this.runRepo = runRepo;
        this.vs = vs;
        this.eventVenueDTOmapper = evDTO;
    }

    @Override
    public List<EventVenueDTO> listEvents() {
        return eventRepo.findAll().stream().map(eventVenueDTOmapper).collect(Collectors.toList());
    }

    @Override
    public EventVenueDTO getEventVenueById(Long eventId) {
        Optional<Event> e = eventRepo.findById(eventId);
        if (e.isEmpty())
            return null;

        return e.map(eventVenueDTOmapper).get();
    }

    @Override
    public Event getEventById(Long id) {
        Optional<Event> e = eventRepo.findById(id);
        if (e.isEmpty())
            return null;

        return e.get();
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
        e.setVenue(newEventInfo.getVenue());
        // e.setRuns(newEventInfo.getRuns());
        // e.setCats(newEventInfo.getCats());
        eventRepo.save(e);

        return e;
    }

    /**
     * Remove a event with the given id
     * Spring Data JPA does not return a value for delete operation
     */
    @Override
    public Event deleteEventById(Long eventId) {
        Event event = getEventById(eventId);
        if (event == null) {
            return null;
        }
        eventRepo.deleteById(eventId);
        return event;
    }

    @Override
    public Venue getVenueOfEvent(Long eventId) {
        Event e = getEventById(eventId);
        if (e == null) return null;

        return e.getVenue();
    }

    @Override
    public Event setVenueForEvent(Long eventId, Long venueId) {
        Event e = getEventById(eventId);
        Venue v = vs.getVenueById(venueId);
        if (e == null || v == null) return null;
        e.setVenue(v);

        // Update database
        updateEvent(eventId, e);
        return e;
    }

    @Override
    public Event addNewRunToEvent(Long eventId, Run run) {
        Event e = getEventById(eventId);
        if (e == null || run == null) return null;

        // Add new run to database
        run.setEvent(e);
        runRepo.save(run);

        return e;
    }

    @Override
    public Event addNewCatToEvent(Long eventId, Category cat) {
        Event e = getEventById(eventId);
        if (e == null || cat == null) return null;

        // Add new cat to database
        cat.setEvent(e);
        catRepo.save(cat);

        // Update database
        // updateEvent(eventId, e);
        return e;
    }

    @Override
    public List<Category> getCatsOfEvent(Long id) {
        Event e = getEventById(id);
        if (e == null) return null;

        return e.getCats();
    }

    @Override
    public Event assignCatsToSeats(Long eventId, int startSeatNum, int endSeatNum, Category cat) {
        Event e = getEventById(eventId);
        if (e == null) return null;

        Venue v = e.getVenue();
        if (v == null) return null;

        // Assign new cat to range of seats
        for (int i = startSeatNum; i <= endSeatNum; i++) {
            Seat s = vs.getSeatByNum(eventId, i);
            if (s == null) break;

            s.setCat(cat);
        }

        // Update the database
        updateEvent(eventId, e);

        return e;

    }
}
