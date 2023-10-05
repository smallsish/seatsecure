package com.seatsecure.backend.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seatsecure.backend.entities.Event;
import com.seatsecure.backend.entities.EventVenueDTO;
import com.seatsecure.backend.entities.EventVenueDTOmapper;
import com.seatsecure.backend.repositories.EventRepository;

@Service
public class EventServiceImpl implements EventService {
   
    private EventRepository eventRepo;
    private EventVenueDTOmapper eventVenueDTOmapper;

    @Autowired
    public EventServiceImpl(EventRepository eventRepo, EventVenueDTOmapper evDTO){
        this.eventRepo = eventRepo;
        this.eventVenueDTOmapper = evDTO;
    }

    @Override
    public List<EventVenueDTO> listEvents() {
        return eventRepo.findAll().stream().map(eventVenueDTOmapper).collect(Collectors.toList());
    }

    
    @Override
    public EventVenueDTO getEventVenueById(Long eventId){
        // Using Java Optional, as "findById" of Spring JPA returns an Optional object
        // Optional forces developers to explicitly handle the case of non-existent values
        // Here is an implementation using lambda expression to extract the value from Optional<Book>
        Optional<Event> e = eventRepo.findById(eventId);
        if (e.isEmpty()) return null;

        return e.map(eventVenueDTOmapper).get();
        // return eventRepo.findById(eventId).map(e -> {
        //     return e;
        // }).orElse(null);
    }

    
    @Override
    public Event addEvent(Event u) {
        return eventRepo.save(u);
    }
    
    @Override
    public Event updateEvent(Long id, Event newEventInfo){
        Optional<Event> event = eventRepo.findById(id);
        if (event.isEmpty()) return null; // Event not found

        Event e = event.get();
        e.setName(newEventInfo.getName());
        e.setStartDate(newEventInfo.getStartDate());
        e.setEndDate(newEventInfo.getEndDate());
        e.setVenue(newEventInfo.getVenue());
        eventRepo.save(e);

        return e;
    }

    /**
     * Remove a event with the given id
     * Spring Data JPA does not return a value for delete operation
     */
    @Override
    public Event deleteEventById(Long eventId){
        Event event = getEventById(eventId);
        if (event == null) {
            return null;
        }
        eventRepo.deleteById(eventId);
        return event;
    }

    @Override
    public Event getEventById(Long id) {
        // TODO Auto-generated method stub
        Optional<Event> e = eventRepo.findById(id);
        if (e.isEmpty()) return null;
        return e.get();
    }
}
