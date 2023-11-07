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
import com.seatsecure.backend.entities.QueueEntry;
import com.seatsecure.backend.entities.Run;
import com.seatsecure.backend.entities.TicketUserQueue;
import com.seatsecure.backend.entities.User;
import com.seatsecure.backend.entities.enums.Status;
import com.seatsecure.backend.exceptions.EventCreationError;
import com.seatsecure.backend.exceptions.QueueEntryNotFoundException;
import com.seatsecure.backend.exceptions.QueueNotFoundException;
import com.seatsecure.backend.services.Algo;
import com.seatsecure.backend.services.QueueEntryService;
import com.seatsecure.backend.services.RunService;
import com.seatsecure.backend.services.TicketQueueService;

@RequestMapping("/api/v1")
@RestController
public class TicketUserQueueController {
    private TicketQueueService ts;
    private QueueEntryService qs;
    private RunService rs;
    private Algo algo;

    public TicketUserQueueController(TicketQueueService ts, QueueEntryService qs, RunService rs, Algo algo){
        this.ts = ts;
        this.qs = qs;
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
        TicketUserQueue queue = ts.getQueue(id);
        if(queue == null) throw new QueueEntryNotFoundException(id);
        
        return queue;

        
    }

    /**
     * Add a new queue entry with POST request to "/events"
     * @param event
     * @return The new event that was added
    */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/run/{runID}")
    public Long newQueueEntry(@PathVariable Long runID, User user, int numOfSeats, Category cat) {
        Run run = rs.getRunById(runID); 
        Long queueID = ts.getQueuePerRunPerCat(cat, run);
        TicketUserQueue queue = ts.getQueue(queueID);
        if(queue == null) throw new QueueEntryNotFoundException(queueID);
        Long id = qs.addEntryToQueue(user, numOfSeats, queue); 
        
        if (id == null) throw new EventCreationError();
        
        return id;

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
    @DeleteMapping("/queue/{queueID}/entry/{entryID}")
    public QueueEntry deleteQueueEntry(@PathVariable Long queueID, @PathVariable Long entryID){
        TicketUserQueue queue = ts.getQueue(queueID); // TO BE IMPLEMENTED
        if(queue == null) throw new QueueNotFoundException(queueID);
        QueueEntry entry = qs.getQueueEntry(entryID);
        if(entry == null) throw new QueueEntryNotFoundException(entryID);
        qs.deleteQueueEntry(entryID);
        return entry; 
        
    }

    // @ResponseStatus(HttpStatus.OK)
    // @GetMapping("/entry/{entryID}")
    // public Status getQueueEntryStatus(@PathVariable Long entryID){
    //     QueueEntry entry = qs.getQueueEntry(entryID);
    //     if (entry == null){
    //         return null;
    //     }
    //     return entry.getStatus();
    // }

    /**
     * Remove a event with the DELETE request to "/event/{id}"
     * If there is no event with the given "id", throw an EventNotFoundException
     * @param id
     */
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/queue/{queueID}")
    public TicketUserQueue deleteQueue(@PathVariable Long queueID){
        TicketUserQueue queue = ts.getQueue(queueID); // TO BE IMPLEMENTED
        if(queue == null) throw new QueueNotFoundException(queueID);
        ts.deleteQueue(queueID);
        
        return queue; 
        
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/run/{run_id}/biddingstart")
    public void biddingstart(@PathVariable Long run_id){
        Run run = rs.getRunById(run_id);
        algo.algoForBidding(run);
    }
}
