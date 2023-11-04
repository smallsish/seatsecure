package com.seatsecure.backend.services;

import java.util.List;

import com.seatsecure.backend.entities.Run;
import com.seatsecure.backend.entities.Seat;
import com.seatsecure.backend.entities.Ticket;
import com.seatsecure.backend.entities.TicketUserQueue;
import com.seatsecure.backend.entities.Venue;

public interface RunService {
    List<Run> listRuns();
    Run getRunById(Long id);
    Run addRun(Run r); // need to check with javier on this
    Run updateRun(Long id, Run newRunInfo);
    Run deleteRunById(Long id);

    List<TicketUserQueue> getQueues(Long runId);
    List<Ticket> getTickets(Long runId);
    
}
