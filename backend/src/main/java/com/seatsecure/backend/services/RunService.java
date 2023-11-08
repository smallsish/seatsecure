package com.seatsecure.backend.services;

import java.time.LocalDateTime;
import java.util.List;

import com.seatsecure.backend.entities.Event;
import com.seatsecure.backend.entities.Run;
import com.seatsecure.backend.entities.TicketUserQueue;

public interface RunService {

    Run getRunById(Long id);
    Event getEventOfRun(Long runId);
    List<Run> getRunsOfEvent(Long eventId);
    List<TicketUserQueue> getTuQueueofRun(Long runId);
    //List<TicketUserQueue> getQueuesOfRun(Long runId);

    Run addNewRunToEvent(Long eventId, Run run);
    Run updateRun(Long id, Run newRunInfo);

    Run deleteRunById(Long runId);


    // Validation methods
    Boolean dateValidAtVenue(Long venueId, LocalDateTime startRunDate, LocalDateTime endRunDate);
    Boolean runDatesAreValid(Run run);

    
}
