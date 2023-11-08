package com.seatsecure.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.seatsecure.backend.entities.Category;
import com.seatsecure.backend.entities.Run;
import com.seatsecure.backend.entities.Seat;
import com.seatsecure.backend.entities.Ticket;
import com.seatsecure.backend.entities.User;
import com.seatsecure.backend.exceptions.not_found.RunNotFoundException;
import com.seatsecure.backend.exceptions.not_found.TicketNotFoundException;
import com.seatsecure.backend.exceptions.not_found.UserNotFoundException;
import com.seatsecure.backend.exceptions.null_property.NullCatException;
import com.seatsecure.backend.exceptions.null_property.NullSeatException;
import com.seatsecure.backend.repositories.SeatRepository;
import com.seatsecure.backend.repositories.TicketRepository;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@Lazy
@Service
public class TicketAccessorServiceImpl implements TicketAccessorService {

    // Dependencies are lazily injected with setters
    private RunService runService;
    private UserService userService;
    private CatService catService;
    private SeatService seatService;
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
     * Get the price of a Ticket
     * 
     * @param ticketId
     * @return The price of the Ticket as a double
     * @throws TicketNotFoundException If a Ticket with the specified id does not
     *                                 exist
     * @throws NullCatException If the Ticket does not have a Category
     */
    @Override
    public Double getPriceOfTicket(Long ticketId) {
        // Try to get ticket's cat
        Category cat = getCatOfTicket(ticketId);

        return catService.getPriceOfCat(cat.getId());
    }

        /**
     * Get the Category of a Ticket
     * 
     * @param ticketId
     * @return The Category of the Ticket
     * @throws TicketNotFoundException If a Ticket with the specified id does not
     *                                 exist
     * @throws NullSeatException If the Ticket does not have a Seat

     */
    @Override
    public Category getCatOfTicket(Long ticketId) {
        // Try to get ticket
        Ticket t = getTicketById(ticketId);

        // Try to get seat
        Seat s = t.getSeat();
        if (s == null) {
            throw new NullSeatException();
        }

        // Try to get cat
        Category c = s.getCat();
        if (c == null) {
            throw new NullCatException();
        }

        return c;

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

    //////////
    @Override
    public Boolean userOwnsTicket(Long userId, Long ticketId) {
        // Check if user exists
        User u = userService.getUserById(userId);

        // Check if ticket exists
        Ticket t = getTicketById(ticketId);

        // Get owner of ticket
        User owner = getOwnerOfTicket(t.getId());

        if (owner == null || u == null) return false;
        
        return owner.getId() == u.getId();
    }

    /*
     * SETTER INJECTORS
     */

    @Autowired
    public void injectRunService(RunService rs) {
        runService = rs;
    }

    @Autowired
    public void injectUserService(UserService us) {
        userService = us;
    }

    @Autowired
    public void injectSeatService(SeatService ss) {
        seatService = ss;
    }

    @Autowired
    public void injectCatRepo(CatService cs) {
        catService = cs;
    }

    @Autowired
    public void injectTicketRepo(TicketRepository tr) {
        ticketRepo = tr;
    }

    @Autowired
    public void injectSeatRepo(SeatRepository sr) {
        seatRepo = sr;
    }
}
