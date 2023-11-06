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

    List<Ticket> addNewTicketsToSeats(long fromSeatNum, long toSeatNum);
    List<Ticket> addNewTicketsToSeats(List<Long> seatIds);
    Ticket unassignTicketFromSeat(long ticketId);
    Ticket deleteTicketById(Long ticketId);

    Run assignTicketsToRun(Long runId, long fromTicketId, long toTicketId);
    Run assignTicketsToRun(Long runId, List<Long> ticketIds);
    Ticket unassignTicketFromRun(long ticketId);

    User assignTicketToUser(Long userId, Long ticketId);
    Ticket unassignTicketFromuser(Long ticketId);

}
