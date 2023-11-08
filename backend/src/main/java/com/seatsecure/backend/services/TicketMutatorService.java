package com.seatsecure.backend.services;

import java.util.List;
import com.seatsecure.backend.entities.Ticket;

public interface TicketMutatorService {

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
