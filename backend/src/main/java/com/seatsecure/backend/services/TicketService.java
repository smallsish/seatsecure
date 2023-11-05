package com.seatsecure.backend.services;

import java.util.List;

import com.seatsecure.backend.entities.Run;
import com.seatsecure.backend.entities.Seat;
import com.seatsecure.backend.entities.Ticket;
import com.seatsecure.backend.entities.User;

public interface TicketService {

    Ticket getTicketById(Long id);

    List<Ticket> getTicketsOfRun(Long runId);

    List<Ticket> getTicketsOfUser(Long userId);

    Ticket deleteTicketFromuser(Long id);

    List<Ticket> addNewTicketsToSeats(int fromSeatNum, int toSeatNum);

    List<Ticket> addNewTicketsToSeats(List<Seat> seats);

    Run assignTicketsToRun(Long runId, int fromTicketId, int toTicketId);

    Run assignTicketsToRun(Long runId, List<Ticket> tickets);

    User assignTicketToUser(Long userId, Long ticketId);

    Run getRunOfTicket(Long id);

    User getUserOfTicket(Long id);

    List<Seat> getSeatsWithoutTickets();
}
