package com.seatsecure.backend.services;

import java.util.List;

import com.seatsecure.backend.entities.Category;
import com.seatsecure.backend.entities.Run;
import com.seatsecure.backend.entities.Seat;
import com.seatsecure.backend.entities.Ticket;
import com.seatsecure.backend.entities.User;

public interface TicketAccessorService {

    Ticket getTicketById(Long ticketId);
    Run getRunOfTicket(Long ticketId);
    User getOwnerOfTicket(Long ticketId);
    Double getPriceOfTicket(Long ticketId);
    Category getCatOfTicket(Long ticketId);

    List<Ticket> getTicketsOfRun(Long runId);
    List<Ticket> getTicketsOfUser(Long userId);
    List<Seat> getSeatsWithoutTickets();

    Boolean userOwnsTicket(Long userId, Long ticketId);
}
