package com.seatsecure.backend.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.seatsecure.backend.entities.Category;
import com.seatsecure.backend.entities.Run;
import com.seatsecure.backend.entities.TicketUserQueue;
import com.seatsecure.backend.entities.User;
import com.seatsecure.backend.services.Algo;
import com.seatsecure.backend.services.RunService;
import com.seatsecure.backend.services.TicketQueueService;

@RequestMapping("/api/v1")
@RestController
public class TicketUserQueueController {
    private TicketQueueService ts;
    private RunService rs;
    private Algo algo;

    public TicketUserQueueController(TicketQueueService ts, RunService rs, Algo algo){
        this.ts = ts;
        this.rs = rs;
        this.algo = algo;
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
        // TicketUserQueue queue = ts.getQueueEntry(id); // TO BE IMPLEMENTED
        // if(queue == null) throw new QueueEntryNotFoundException(id);
        
        // return queue;

        return null;
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
        //Long id = ts.addUserToQueue(user, cat, run, numOfSeats); // TO BE IMPLEMENTED
        
        // if (id == null) throw new EventCreationError();
        
        // return id;

        return null;
    }


    // /**
    //  * Update an event with new info
    //  * If there is no event with the given "id", throw an EventNotFoundException
    //  * @param id
    //  * @param newEventInfo
    //  * @return the updated event
    //  */
    // @ResponseStatus(HttpStatus.OK)
    // @PutMapping("/events/{id}")
    // @PreAuthorize("hasAuthorities('admin:update')")
    // public Event updateEvent(@PathVariable Long id, @Valid @RequestBody Event newEventInfo){
    //     Event event = es.updateEvent(id, newEventInfo);
    //     if(event == null) throw new EventNotFoundException(id);
        
    //     return event;
    // }

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
        // TODO - if
        return null; 
        
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/run/{run_id}/biddingstart")
    @PreAuthorize("hasAuthority('admin:create')")
    public void biddingstart(@PathVariable Long run_id){
        Run run = rs.getRunById(run_id);
        algo.algoForBidding(run);
    }
}
