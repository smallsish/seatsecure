package com.seatsecure.backend.services;

import java.util.Date;
import java.util.List;

import com.seatsecure.backend.entities.Event;
import com.seatsecure.backend.entities.Run;
import com.seatsecure.backend.entities.TicketUserQueue;
import com.seatsecure.backend.entities.Venue;

public interface RunService {

    Run getRunById(Long id);

    Event getEventOfRun(Long runId);

    List<Run> getRunsOfEvent(Long eventId);

    Run addNewRunToEvent(Long eventId, Run run);
    
    Run updateRun(Long id, Run newRunInfo);

    Run deleteRunById(Long runId);

    List<TicketUserQueue> getTuQueueofRun(Long runId);

    // Validation methods
    Boolean dateValidAtVenue(Long venueId, Date startDate, Date endDate);

    Boolean runDatesAreValid(Run run);

}
