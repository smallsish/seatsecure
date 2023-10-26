package com.seatsecure.backend.services;

import java.util.List;

import com.seatsecure.backend.entities.Run;
import com.seatsecure.backend.entities.Ticket;
import com.seatsecure.backend.entities.User;

public interface TicketService {
    
    Ticket getTicketById(Long id);
    
    List<Ticket> getTicketsOfRun(Long runId);
    List<Ticket> getTicketsOfUser(Long userId);

    Run getRunOfTicket(Long id);

    User getOwner(Long id);
}
