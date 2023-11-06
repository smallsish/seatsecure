package com.seatsecure.backend.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.seatsecure.backend.entities.Run;
import com.seatsecure.backend.entities.Seat;
import com.seatsecure.backend.entities.Ticket;
import com.seatsecure.backend.entities.User;
import com.seatsecure.backend.exceptions.category.CatNotFoundException;
import com.seatsecure.backend.exceptions.run.NullRunException;
import com.seatsecure.backend.exceptions.seat.NullSeatException;
import com.seatsecure.backend.exceptions.seat.SeatHasTicketException;
import com.seatsecure.backend.exceptions.seat.SeatNotFoundException;
import com.seatsecure.backend.exceptions.ticket.TicketHasOwnerException;
import com.seatsecure.backend.exceptions.ticket.TicketHasRunException;
import com.seatsecure.backend.exceptions.ticket.TicketNotFoundException;
import com.seatsecure.backend.exceptions.user.NullUserException;
import com.seatsecure.backend.repositories.SeatRepository;
import com.seatsecure.backend.repositories.TicketRepository;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@Service
public class TicketServiceImpl implements TicketService {

    // Due to the large number of dependencies, lazy setter injection is used
    // (setter injectors are below)
    private RunService runService;
    private SeatService seatService;
    private UserService userService;
    private TicketService ticketService;
    private TicketRepository ticketRepo;
    private SeatRepository seatRepo;

    /**
     * Get the Ticket of the specified id
     * 
     * @param ticketId
     * @return The Ticket of the specified id
     * @throws TicketNotFoundException If a Ticket with the specified id does not
     *                                 exist
     */
    @Override
    public Ticket getTicketById(Long ticketId) {
        Optional<Ticket> ticket = ticketRepo.findById(ticketId);
        if (ticket.isEmpty()) {
            throw new TicketNotFoundException(ticketId);
        }

        return ticket.get();
    }

    /**
     * Get the owner User of a Ticket
     * 
     * @param ticketId
     * @return The User which owns the Ticket with the specified id, or null if there isn't one
     * @throws TicketNotFoundException If a Ticket with the specified id does not
     *                                 exist
     */
    @Override
    public User getOwnerOfTicket(Long ticketId) {
        // Check if ticket exists
        Ticket ticket = getTicketById(ticketId);

        // Check if ticket has an owner
        User owner = ticket.getUser();
        return owner; // Returns null if there is no owner
    }

    /**
     * Get the Run of a Ticket
     * 
     * @param ticketId
     * @return The Run which the Ticket of the specified id is associated with, or null if it isn't associated with one
     * @throws TicketNotFoundException If a Ticket with the specified id does not
     *                                 exist
     */
    @Override
    public Run getRunOfTicket(Long ticketId) {
        // Check if ticket exists
        Ticket ticket = getTicketById(ticketId);

        // Get run of ticket
        Run run = ticket.getRun();
        return run; // Returns null if there is no run it is associated with
    }

    /**
     * Get the Tickets in a Run with the specified id
     * 
     * @param runId
     * @return A list of Tickets in the Run with the specified id
     * @throws RunNotFoundException If a Run with the specified id does not
     *                              exist
     */
    @Override
    public List<Ticket> getTicketsOfRun(Long runId) {

        // Check if run exists
        Run run = runService.getRunById(runId);

        // Get tickets of run
        return ticketRepo.findByRun(run);
    }

    /**
     * Get the Tickets belonging to a User
     * 
     * @param userId
     * @return A list of Tickets of the User with the specified id
     * @throws UserNotFoundException If a User with the specified id does not
     *                               exist
     */
    @Override
    public List<Ticket> getTicketsOfUser(Long userId) {
        // Check if user exists
        User user = userService.getUserById(userId);

        // Get tickets owned by user
        return ticketRepo.findByUser(user);
    }

    /**
     * Get all Seats that don't have Tickets associated with them yet
     * 
     * @return A list of Seats that don't have Tickets associated with them
     */
    @Override
    public List<Seat> getSeatsWithoutTickets() {
        List<Seat> seats = seatRepo.findAll();
        return seats.stream().filter(s -> s.getTicket() == null).toList();
    }



    /**
     * Create new Tickets and assign them to Seats
     * 
     * @param ticketId
     * @return A list of the newly added Tickets
     * @throws SeatNotFoundException  If a Seat with the specified id does not
     *                                exist
     * @throws SeatHasTicketException If the Seat already has a Ticket associated
     *                                with it
     */
    @Override
    public List<Ticket> addNewTicketsToSeats(long fromSeatNum, long toSeatNum) {
        List<Ticket> ticketList = new ArrayList<>();

        for (long id = fromSeatNum; id <= toSeatNum; id++) {
            // Check if seat exists
            Seat s = seatService.getSeatById(id);

            // Check if seat already has a ticket
            if (s.getTicket() != null) {
                throw new SeatHasTicketException(id);
            }

            // Create a new Ticket and assign it to the seat
            Ticket t = Ticket.builder().seat(s).run(null).user(null).build();
            ticketRepo.save(t);
            ticketList.add(t);
        }

        return ticketList;
    }

    /**
     * OVERLOADED METHOD
     * Create new Tickets and assign them to Seats
     * 
     * @param seatIds - A list of ids of Seats, to which new Tickets should be
     *                created and added
     * @return A list of the newly added Tickets
     * @throws SeatNotFoundException  If a Seat with the specified id does not
     *                                exist
     * @throws SeatHasTicketException If the Seat already has a Ticket associated
     *                                with it
     */
    @Override
    public List<Ticket> addNewTicketsToSeats(List<Long> seatIds) {
        List<Ticket> ticketList = new ArrayList<>();

        for (long id : seatIds) {
            // Check if seat exists
            Seat s = seatService.getSeatById(id);

            // Check if seat already has a ticket
            if (s.getTicket() != null) {
                throw new SeatHasTicketException(id);
            }

            // Create a new Ticket and assign it to the seat
            Ticket t = Ticket.builder().seat(s).run(null).user(null).build();
            ticketRepo.save(t);
            ticketList.add(t);
        }

        return ticketList;
    }

    /**
     * Unassign a Ticket from the Seat it is currently associated with
     * 
     * @param ticketId
     * @return The Ticket that has been unassigned from its Seat
     * @throws TicketNotFoundException If a Ticket with the specified id does not
     *                                 exist
     */
    @Override
    public Ticket unassignTicketFromSeat(long ticketId) {
        // Check if ticket exists
        Ticket t = ticketService.getTicketById(ticketId);

        // Check if ticket isn't already associated with a Seat
        if (t.getSeat() == null) {
            throw new NullSeatException();
        }

        // Unassign ticket from its current seat
        t.setSeat(null);
        ticketRepo.save(t);
        return t;
    }

    /**
     * Delete the Ticket with the specified id
     * 
     * @param ticketId
     * @return The Ticket that has been deleted
     * @throws TicketNotFoundException If a Ticket with the specified id does not
     *                                 exist
     */
    @Override
    public Ticket deleteTicketById(Long ticketId) {
        // Check if ticket exists
        Ticket t = getTicketById(ticketId);

        // Check if ticket has an owner
        if (t.getUser() != null) {
            throw new TicketHasOwnerException(ticketId);
        }

        // Delete the ticket
        ticketRepo.deleteById(t.getId());
        return t;
    }

    /**
     * Assign Tickets to a Run
     * 
     * @param runId
     * @param fromTicketId
     * @param toTicketId
     * @return The Run to which Tickets have been assigned
     * @throws RunNotFoundException    If a Run with the specified id does not exist
     * @throws TicketNotFoundException If a Ticket with the specified id does not
     *                                 exist
     */
    @Override
    public Run assignTicketsToRun(Long runId, long fromTicketId, long toTicketId) {
        // Check if run exists
        Run run = runService.getRunById(runId);

        // Assign tickets to run
        for (long id = fromTicketId; id <= toTicketId; id++) {
            // Check if ticket exists
            Ticket t = ticketService.getTicketById(id);

            // Check if ticket is already associated with a run
            if (t.getRun() != null) {
                throw new TicketHasRunException(id);
            }

            // Update the run property of the ticket
            t.setRun(run);
            ticketRepo.save(t);
        }

        return run;

    }

    /**
     * OVERLOADED METHOD
     * Assign Tickets to a Run
     * 
     * @param runId
     * @param ticketIds - A list of ids of Tickets that should be assigned to the
     *                  specified Run
     * @return The Run to which Tickets have been assigned
     * @throws RunNotFoundException    If a Run with the specified id does not exist
     * @throws TicketNotFoundException If a Ticket with the specified id does not
     *                                 exist
     */
    @Override
    public Run assignTicketsToRun(Long runId, List<Long> ticketIds) {
        // Check if run exists
        Run run = runService.getRunById(runId);

        for (Long id : ticketIds) {
            // Check if ticket exists
            Ticket t = ticketService.getTicketById(id);

            // Check if ticket is already associated with a run
            if (t.getRun() != null) {
                throw new TicketHasRunException(id);
            }

            // Update the run property of the ticket
            t.setRun(run);
            ticketRepo.save(t);
        }

        return run;

    }

    /**
     * Unassign a Ticket from the Run it is currently associated with
     * 
     * @param ticketId
     * @return The Ticket that has been unassigned from its Run
     * @throws TicketNotFoundException If a Ticket with the specified id does not
     *                                 exist
     */
    @Override
    public Ticket unassignTicketFromRun(long ticketId) {
        // Check if ticket exists
        Ticket t = ticketService.getTicketById(ticketId);

        // Check if ticket isn't already associated with a run
        if (t.getRun() == null) {
            throw new NullRunException();
        }

        // Unassign ticket from its current run
        t.setRun(null);
        ticketRepo.save(t);
        return t;
    }

    /**
     * Assign a Ticket to a User
     * 
     * @param userId
     * @param ticketId
     * @return The User to which the TIcket has been assigned
     * @throws UserNotFoundException If a User with the specified id does not
     *                                 exist
     * @throws TicketNotFoundException If a Ticket with the specified id does not
     *                                 exist
     */
    @Override
    public User assignTicketToUser(Long userId, Long ticketId) {
        // Check if user exists
        User user = userService.getUserById(userId);

        // Check if ticket exists
        Ticket ticket = ticketService.getTicketById(ticketId);

        // Check if ticket already has a user
        if (ticket.getUser() != null) {
            throw new TicketHasOwnerException(ticketId);
        }

        //  Update the user property of the ticket
        ticket.setUser(user);
        ticketRepo.save(ticket);

        return user;
    }

    /**
     * Unassign a Ticket from its owner User
     * 
     * @param ticketId
     * @return The unassigned Ticket
     * @throws TicketNotFoundException If a Ticket with the specified id does not
     *                                 exist
     * @throws NullUserException       If the Ticket does not already have an owner
     */
    @Override
    public Ticket unassignTicketFromuser(Long ticketId) {
        // Check if ticket exists
        Ticket t = getTicketById(ticketId);

        // Check if ticket doesn't already have an owner
        if (t.getUser() == null) {
            throw new NullUserException();
        }

        t.setUser(null);
        return ticketRepo.save(t);
    }


    /*
     * SETTER INJECTORS
     */

    @Lazy
    @Autowired
    public void injectRunService(RunService rs) {
        runService = rs;
    }

    @Lazy
    @Autowired
    public void injectSeatService(SeatService ss) {
        seatService = ss;
    }

    @Lazy
    @Autowired
    public void injectUserService(UserService us) {
        userService = us;
    }

    @Lazy
    @Autowired
    public void injectTicketService(TicketService ts) {
        ticketService = ts;
    }

    @Lazy
    @Autowired
    public void injectTicketRepo(TicketRepository tr) {
        ticketRepo = tr;
    }

    @Lazy
    @Autowired
    public void injectSeatRepo(SeatRepository sr) {
        seatRepo = sr;
    }

}
