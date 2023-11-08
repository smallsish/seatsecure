package com.seatsecure.backend.controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.catalina.authenticator.SpnegoAuthenticator.AuthenticateAction;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.seatsecure.backend.entities.Category;
import com.seatsecure.backend.entities.QueueEntry;
import com.seatsecure.backend.entities.Run;
import com.seatsecure.backend.entities.TicketUserQueue;
import com.seatsecure.backend.entities.User;
import com.seatsecure.backend.entities.DTO_mappers.bidEntry.BidEntryDTOmapper;
import com.seatsecure.backend.entities.DTOs.bidEntry.BidEntryDetailsDTO;
import com.seatsecure.backend.exceptions.creation.EventCreationException;
import com.seatsecure.backend.exceptions.creation.QueueEntryCreationException;
import com.seatsecure.backend.exceptions.not_found.CatNotFoundException;
import com.seatsecure.backend.exceptions.not_found.QueueEntryNotFoundException;
import com.seatsecure.backend.exceptions.QueueNotFoundException;
import com.seatsecure.backend.exceptions.not_found.RunNotFoundException;
import com.seatsecure.backend.security.auth.AuthenticationService;
import com.seatsecure.backend.services.Algo;
import com.seatsecure.backend.services.CatService;
import com.seatsecure.backend.services.QueueEntryService;
import com.seatsecure.backend.services.RunService;
import com.seatsecure.backend.services.TicketMutatorService;
import com.seatsecure.backend.services.TicketQueueService;
import com.seatsecure.backend.services.UserService;

@RequestMapping("/api/v1")
@RestController
public class TicketUserQueueController {
    private TicketQueueService ts;
    private QueueEntryService qs;
    private RunService rs;
    private UserService us;
    private CatService cs;
    private TicketMutatorService ticketMutateSer;
    private AuthenticationService as;
    private Algo algo;
    private BidEntryDTOmapper beDTOmapper;

    public TicketUserQueueController(TicketQueueService ts, QueueEntryService qs,
    RunService rs, UserService us, CatService cs, AuthenticationService as, Algo algo, 
    BidEntryDTOmapper beDTOmapper, TicketMutatorService ticketMutateSer){
        this.ts = ts;
        this.qs = qs;
        this.rs = rs;
        this.us = us;
        this.cs = cs;
        this.as = as;
        this.algo = algo;
        this.beDTOmapper = beDTOmapper;
        this.ticketMutateSer = ticketMutateSer;
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
    @PostMapping("/queue-entry/{catId}/{runId}")
    public Long newQueueEntry(@PathVariable Long catId, @PathVariable Long runId, Integer numOfSeats) {
        numOfSeats = 2;

        // Get current user
        UserDetails ud = as.getCurrentUserDetails();
        User user = us.getUserByUsername(ud.getUsername());

        // Get run the user wants to bid for
        Run run = rs.getRunById(runId); 

        // Get cat the user wants to bid for
        Category cat = cs.getCatById(catId); 

        // Get queueId
        TicketUserQueue queue = ts.getQueuePerRunPerCat(cat.getId(), run.getId()); // RETURNING NULL

        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime startTime = run.getStartBidDate();
        LocalDateTime endTime = run.getEndBidDate();
        if (currentTime.isAfter(startTime) && currentTime.isBefore(endTime)){
            Long id = qs.addEntryToQueue(user, numOfSeats, queue); 
        
            if (id == null) throw new EventCreationException();
            
            return id;
        }   else{
            throw new QueueEntryCreationException(user, numOfSeats, cat, run);
        }
    
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/queue-entry/{catId}/{runId}")
    public BidEntryDetailsDTO getQueueEntry(@PathVariable Long runId, @PathVariable Long catId) {
        // Get current user
        UserDetails ud = as.getCurrentUserDetails();
        User user = us.getUserByUsername(ud.getUsername());

        List<QueueEntry> queueEntries = qs.getQueueEntriesOfUser(user.getId());
        for (QueueEntry q : queueEntries) {
            TicketUserQueue tuQueue = q.getTuQueue();
            Category cat = tuQueue.getCat();
            Run run = tuQueue.getRun();

            if (cat.getId() == catId && run.getId() == runId) {
                return beDTOmapper.apply(q);
            }
        }

        throw new QueueEntryNotFoundException();
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

    // /**
    //  * Remove a event with the DELETE request to "/event/{id}"
    //  * If there is no event with the given "id", throw an EventNotFoundException
    //  * @param id
    //  */
    // @ResponseStatus(HttpStatus.OK)
    // @DeleteMapping("/queue/{queue_id}/entry{entry_id}")
    // @PreAuthorize("hasAuthority('admin:delete')")
    // public Long deleteQueueEntry(@PathVariable Long queue_id, @PathVariable Long entry_id){
    //     TicketUserQueue queue = getTicketUserQueue(queue_id);
    //     // TODO - if
    //     return null; 
    // }
    @DeleteMapping("/queue/{queueID}/entry/{entryID}")
    public QueueEntry deleteQueueEntry(@PathVariable Long queueID, @PathVariable Long entryID){
        TicketUserQueue queue = ts.getQueue(queueID); 
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
        TicketUserQueue queue = ts.getQueue(queueID); 
        if(queue == null) throw new QueueNotFoundException(queueID);
        ts.deleteQueue(queueID);
        
        return queue; 
        
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/run/{run_id}/biddingstart")
    public void biddingstart(@PathVariable Long run_id){
        Run run = rs.getRunById(run_id);
        algo.algoForBidding(run);
        ticketMutateSer.assignTicketToUser((long) 2, (long) 7);
    }
}
