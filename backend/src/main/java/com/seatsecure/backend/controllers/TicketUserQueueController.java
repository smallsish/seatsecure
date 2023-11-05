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

import com.seatsecure.backend.entities.Category;
import com.seatsecure.backend.entities.Event;
import com.seatsecure.backend.entities.EventVenueDTO;
import com.seatsecure.backend.entities.Run;
import com.seatsecure.backend.entities.TicketUserQueue;
import com.seatsecure.backend.entities.User;
import com.seatsecure.backend.exceptions.EventCreationError;
import com.seatsecure.backend.exceptions.EventNotFoundException;
import com.seatsecure.backend.exceptions.QueueEntryNotFoundException;
import com.seatsecure.backend.services.EventService;
import com.seatsecure.backend.services.QueueEntryServiceImpl;
import com.seatsecure.backend.services.RunService;
import com.seatsecure.backend.services.SeatService;
import com.seatsecure.backend.services.TicketQueueService;
import com.seatsecure.backend.services.UserService;

@RequestMapping("/api/v1")
@RestController
public class TicketUserQueueController {
    private TicketQueueService ts;
    private QueueEntryServiceImpl qs;
    private UserService us;
    private RunService rs;
    private EventService es;
    private SeatService ss;

    @Autowired
    public TicketUserQueueController(TicketQueueService ts, UserService us, RunService rs, EventService es, SeatService ss){
        ts = ts;
        us = us;
        rs = rs;
        es = es;
        ss = ss;
    }

    /**
     * List all existing queue
     * @return list of all 
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("queue")
    public List<TicketUserQueue> getQueue(){
        return ts.listAllQueues();
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
     * Add a new queue entry with POST request to "/events"
     * @param event
     * @return The new event that was added
    */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/queue/{id}")
    @PreAuthorize("hasAuthorities('admin:create')")
    public Long addQueueEntry(@PathVariable Long queueId, User user, Category cat, int numOfSeats, Run run) {
        Long id = ts.addUserToQueue(user, cat, run, numOfSeats);
        
        if (id == null) throw new EventCreationError();
        
        return id;
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
    @DeleteMapping("/queue/{queue_id}/entry{entry_id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public Long deleteQueueEntry(@PathVariable Long queue_id, @PathVariable Long entry_id){
        TicketUserQueue queue = getTicketUserQueue(queue_id);
        if 
        
    }
}
