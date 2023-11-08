package com.seatsecure.backend.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.seatsecure.backend.entities.Category;
import com.seatsecure.backend.entities.Run;
import com.seatsecure.backend.entities.Seat;
import com.seatsecure.backend.entities.Ticket;
import com.seatsecure.backend.entities.User;
import com.seatsecure.backend.exceptions.existence.SeatHasTicketException;
import com.seatsecure.backend.exceptions.existence.TicketHasOwnerException;
import com.seatsecure.backend.exceptions.existence.TicketHasRunException;
import com.seatsecure.backend.exceptions.not_found.RunNotFoundException;
import com.seatsecure.backend.exceptions.not_found.SeatNotFoundException;
import com.seatsecure.backend.exceptions.not_found.TicketNotFoundException;
import com.seatsecure.backend.exceptions.not_found.UserNotFoundException;
import com.seatsecure.backend.exceptions.null_property.NullRunException;
import com.seatsecure.backend.exceptions.null_property.NullSeatException;
import com.seatsecure.backend.exceptions.null_property.NullUserException;
import com.seatsecure.backend.repositories.TicketRepository;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@Lazy
@Service
public class TicketMutatorServiceImpl implements TicketMutatorService {

    // Dependencies are lazily injected with setters
    private RunService runService;
    private SeatService seatService;
    private UserService userService;
    private CatService catService;
    private TicketAccessorService ticketService;
    private TicketRepository ticketRepo;

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
    public List<Ticket> addNewTicketsToSeats(Long fromSeatNum, Long toSeatNum) {
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
            Ticket t = Ticket.builder().seat(s).user(null).run(null).build();
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
    public Ticket unassignTicketFromSeat(Long ticketId) {
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
        Ticket t = ticketService.getTicketById(ticketId);

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
     * @return The Tickets that have been assigned to the Run
     * @throws RunNotFoundException    If a Run with the specified id does not exist
     * @throws TicketNotFoundException If a Ticket with the specified id does not
     *                                 exist
     */
    @Override
    public List<Ticket> assignTicketsToRun(Long runId, Long fromTicketId, Long toTicketId) {
        // Check if run exists
        Run run = runService.getRunById(runId);

        List<Ticket> ticketList = new ArrayList<>();
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
            ticketList.add(ticketRepo.save(t));
        }

        return ticketList;
    }

    /**
     * OVERLOADED METHOD
     * Assign Tickets to a Run
     * 
     * @param runId
     * @param ticketIds - A list of ids of Tickets that should be assigned to the
     *                  specified Run
     * @return The Tickets that have been assigned to the Run
     * @throws RunNotFoundException    If a Run with the specified id does not exist
     * @throws TicketNotFoundException If a Ticket with the specified id does not
     *                                 exist
     */
    @Override
    public List<Ticket> assignTicketsToRun(Long runId, List<Long> ticketIds) {
        // Check if run exists
        Run run = runService.getRunById(runId);

        List<Ticket> ticketList = new ArrayList<>();
        for (Long id : ticketIds) {
            // Check if ticket exists
            Ticket t = ticketService.getTicketById(id);

            // Check if ticket is already associated with a run
            if (t.getRun() != null) {
                throw new TicketHasRunException(id);
            }

            // Update the run property of the ticket
            t.setRun(run);
            ticketList.add(ticketRepo.save(t));
        }

        return ticketList;

    }


    // /**
    //  * Assign Tickets to a Category, or more intuitively, giving Tickets a Category
    //  * 
    //  * @param catId
    //  * @return The Tickets that have been assigned to the Run
    //  * @throws RunNotFoundException    If a Run with the specified id does not exist
    //  * @throws TicketNotFoundException If a Ticket with the specified id does not
    //  *                                 exist
    //  */
    // @Override
    // public List<Ticket> assignTicketsToCat(Long catId, Long fromTicketId, Long toTicketId) {

    //     // Check if cat exists
    //     Category c = catService.getCatById(catId);

    //     List<Ticket> ticketList = new ArrayList<>();
    //     // Assign tickets to cat
    //     for (long id = fromTicketId; id <= toTicketId; id++) {
    //         // Check if ticket exists
    //         Ticket t = ticketService.getTicketById(id);

    //         // Check if ticket is already associated with a run
    //         if (t.getRun() != null) {
    //             throw new TicketHasRunException(id);
    //         }

    //         // Update the run property of the ticket
    //         t.setCat(c);
    //         ticketList.add(ticketRepo.save(t));
    //     }

    //     return ticketList;
    // }



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
     * @return The Ticket which has been assigned to the User
     * @throws UserNotFoundException If a User with the specified id does not
     *                                 exist
     * @throws TicketNotFoundException If a Ticket with the specified id does not
     *                                 exist
     * @throws TicketHasOwnerException If the Ticket already has an existing owner
     */
    @Override
    public Ticket assignTicketToUser(Long userId, Long ticketId) {
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
        return ticketRepo.save(ticket);

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
        Ticket t = ticketService.getTicketById(ticketId);

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

    @Autowired
    public void injectRunService(RunService rs) {
        runService = rs;
    }

    @Autowired
    public void injectSeatService(SeatService ss) {
        seatService = ss;
    }

    @Autowired
    public void injectUserService(UserService us) {
        userService = us;
    }

    @Autowired
    public void injectCatService(CatService cs) {
        catService = cs;
    }


    @Autowired
    public void injectTicketAccessorService(TicketAccessorService tms) {
        ticketService = tms;
    }

    @Autowired
    public void injectTicketRepo(TicketRepository tr) {
        ticketRepo = tr;
    }

}
