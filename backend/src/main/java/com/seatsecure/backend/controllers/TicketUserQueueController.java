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
import com.seatsecure.backend.entities.Run;
import com.seatsecure.backend.entities.TicketUserQueue;
import com.seatsecure.backend.exceptions.EventCreationError;
import com.seatsecure.backend.exceptions.EventNotFoundException;
import com.seatsecure.backend.exceptions.QueueEntryNotFoundException;
import com.seatsecure.backend.services.EventService;
import com.seatsecure.backend.services.TicketQueueService;
import com.seatsecure.backend.services.UserService;

@RequestMapping("/api/v1")
@RestController
public class TicketUserController {
    private TicketQueueService ts;
    private UserService us;
    private EventService es;

    @Autowired
    public TicketUserController(TicketQueueService ts, UserService us, EventService es){
        ts = ts;
        us = us;
        es = es;
    }

    /**
     * List all existing queue
     * @return list of all 
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/event/{event_id}/run/{run_id}/queue")
    public List<TicketUserQueue> getQueue(@PathVariable Long event_id, @PathVariable Long run_id){
        
        
    }

    /**
     * Search for event with the given id
     * If there is no event with the given "id", throw a EventNotFoundException
     * @param id
     * @return Event with the given id
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/queue/{id}")
    public TicketUserQueue getTicketUserQueue(@PathVariable Long id){
        TicketUserQueue queue = ts.getQueueEntry(id);
        if(queue == null) throw new QueueEntryNotFoundException(id);
        
        return queue;
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
