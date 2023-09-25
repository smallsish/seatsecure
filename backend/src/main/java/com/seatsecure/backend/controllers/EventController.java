package com.seatsecure.backend.controllers;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.seatsecure.backend.entities.Event;
import com.seatsecure.backend.entities.EventVenueDTO;
import com.seatsecure.backend.exceptions.EventCreationError;
import com.seatsecure.backend.exceptions.EventNotFoundException;
import com.seatsecure.backend.services.EventService;

@RequestMapping("/api/v1")
@RestController
public class EventController {
    private EventService eventService;

    @Autowired
    public EventController(EventService es){
        eventService = es;
    }

    /**
     * List all existing events
     * @return list of all events
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/events")
    public List<EventVenueDTO> getEvents(){
        return eventService.listEvents();
    }

    /**
     * Search for event with the given id
     * If there is no event with the given "id", throw a EventNotFoundException
     * @param id
     * @return Event with the given id
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/events/{id}")
    public EventVenueDTO getEvent(@PathVariable Long id){
        EventVenueDTO event = eventService.getEventVenueById(id);
        if(event == null) throw new EventNotFoundException(id);
        
        return event;
    }

    /**
     * Add a new event with POST request to "/events"
     * @param event
     * @return The new event that was added
    */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/events")
    @PreAuthorize("hasAuthorities('admin:create')")
    public Event addEvent(@Valid @RequestBody Event event) {
        Event e = eventService.addEvent(event);
        
        if (e == null) throw new EventCreationError();
        
        return event;
    }


    /**
     * Update an event with new info
     * If there is no event with the given "id", throw an EventNotFoundException
     * @param id
     * @param newEventInfo
     * @return the updated event
     */
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/events/{id}")
    @PreAuthorize("hasAuthorities('admin:update')")
    public Event updateEvent(@PathVariable Long id, @Valid @RequestBody Event newEventInfo){
        Event event = eventService.updateEvent(id, newEventInfo);
        if(event == null) throw new EventNotFoundException(id);
        
        return event;
    }

    /**
     * Remove a event with the DELETE request to "/event/{id}"
     * If there is no event with the given "id", throw an EventNotFoundException
     * @param id
     */
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/events/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public Event deleteEvent(@PathVariable Long id){

        Event event = eventService.deleteEventById(id);
        if(event == null) throw new EventNotFoundException(id);

        return event;
    }
}
