package com.seatsecure.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.seatsecure.backend.entities.Run;
import com.seatsecure.backend.entities.Ticket;
import com.seatsecure.backend.entities.User;
import com.seatsecure.backend.repositories.TicketRepository;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@Service
public class TicketServiceImpl implements TicketService{
    private TicketRepository ticketRepo;
    private RunService rs;
    private UserService us;

    public TicketServiceImpl(TicketRepository ticketRepo, RunService rs, UserService us) {
        this.ticketRepo = ticketRepo;
        this.rs = rs;
        this.us = us;
    }

    @Override
    public Ticket getTicketById(Long id) {
        Optional<Ticket> ticket = ticketRepo.findById(id);
        if (ticket.isEmpty()) return null;

        return ticket.get();
    }

    @Override
    public List<Ticket> getTicketsOfRun(Long runId) {
        // Check if run exists
        Run run = rs.getRunById(runId);
        if (run == null) return null;

        // Get tickets of run
        return ticketRepo.findByRun(run);
    }

    @Override
    public List<Ticket> getTicketsOfUser(Long userId) {
        // Check if user exists
        User user = us.getUserById(userId);
        if (user == null) return null;

        // Get tickets owned by user
        return ticketRepo.findByUser(user);
    }

    @Override
    public User getOwner(Long id) {
        // Check if ticket exists
        Ticket ticket = getTicketById(id);
        if (ticket == null) return null;

        // Check if ticket has an owner
        User owner = ticket.getUser();
        if (owner == null) return null;

        return owner;
    }

    @Override
    public Run getRunOfTicket(Long id) {
        // Check if ticket exists
        Ticket ticket = getTicketById(id);
        if (ticket == null) return null;

        // Get run of ticket
        Run run = ticket.getRun();

        return run;
    }
    
}
