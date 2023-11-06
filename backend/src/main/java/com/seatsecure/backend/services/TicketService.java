package com.seatsecure.backend.services;

import java.util.List;

import com.seatsecure.backend.entities.Run;
import com.seatsecure.backend.entities.Seat;
import com.seatsecure.backend.entities.Ticket;
import com.seatsecure.backend.entities.User;

public interface TicketService {

    Ticket getTicketById(Long ticketId);
    Run getRunOfTicket(Long ticketId);
    User getOwnerOfTicket(Long ticketId);

    List<Ticket> getTicketsOfRun(Long runId);
    List<Ticket> getTicketsOfUser(Long userId);
    List<Seat> getSeatsWithoutTickets();

    List<Ticket> addNewTicketsToSeats(Long fromSeatNum, Long toSeatNum);
    List<Ticket> addNewTicketsToSeats(List<Long> seatIds);
    Ticket unassignTicketFromSeat(Long ticketId);
    Ticket deleteTicketById(Long ticketId);

    List<Ticket> assignTicketsToRun(Long runId, Long fromTicketId, Long toTicketId);
    List<Ticket> assignTicketsToRun(Long runId, List<Long> ticketIds);
    Ticket unassignTicketFromRun(long ticketId);

    Ticket assignTicketToUser(Long userId, Long ticketId);
    Ticket unassignTicketFromuser(Long ticketId);

}
