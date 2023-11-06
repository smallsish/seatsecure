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
import com.seatsecure.backend.exceptions.seat.SeatNotFoundException;
import com.seatsecure.backend.exceptions.ticket.TicketNotFoundException;
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
     * Get the Tickets in a Run with the specified id
     * 
     * @param runId
     * @return A list of Tickets in the Run with the specified id
     * @throws RunNotFoundException If a Run with the specified id does not
     *                                 exist
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
     *                                 exist
     */
    @Override
    public List<Ticket> getTicketsOfUser(Long userId) {
        // Check if user exists
        User user = userService.getUserById(userId);

        // Get tickets owned by user
        return ticketRepo.findByUser(user);
    }

    
    @Override
    public Ticket deleteTicketFromuser(Long id) {
        // Check if ticket exists
        Ticket t = getTicketById(id);
        if (t == null)
            return null;

        t.setUser(null);
        return ticketRepo.save(t);
    }

    @Override
    public List<Ticket> addNewTicketsToSeats(int fromSeatNum, int toSeatNum) {
        List<Ticket> ticketList = new ArrayList<>();

        for (long i = fromSeatNum; i <= toSeatNum; i++) {
            // Check if seat exists
            Seat s = seatService.getSeatById(i);
            if (s == null)
                continue;

            // Check if seat already has a ticket
            if (s.getTicket() != null)
                return null;

            Ticket t = Ticket.builder().seat(s).run(null).user(null).build();
            ticketRepo.save(t);
            ticketList.add(t);
        }

        return ticketList;
    }

    @Override
    public List<Ticket> addNewTicketsToSeats(List<Seat> seats) {
        List<Ticket> ticketList = new ArrayList<>();

        for (Seat s : seats) {
            if (s == null)
                return null;

            // Check if seat already has a ticket
            if (s.getTicket() != null)
                return null;

            Ticket t = Ticket.builder().seat(s).run(null).user(null).build();
            ticketRepo.save(t);
            ticketList.add(t);
        }

        return ticketList;
    }

    public Ticket deleteTicketById(Long id) {
        // Check if ticket exists
        Ticket t = getTicketById(id);
        if (t == null)
            return null;

        ticketRepo.deleteById(t.getId());
        return t;
    }

    @Override
    public Run assignTicketsToRun(Long runId, int fromTicketId, int toTicketId) {
        // Check if run exists
        // Check if numTickets <= num seats available
        Run run = runService.getRunById(runId);
        if (run == null)
            return null;

        for (long i = fromTicketId; i <= toTicketId; i++) {
            // Check if ticket exists
            Ticket t = ticketService.getTicketById(i);
            if (t == null)
                continue;

            t.setRun(run);
            ticketRepo.save(t);
        }

        return run;

    }

    @Override
    public Run assignTicketsToRun(Long runId, List<Ticket> tickets) {
        // Check if run exists
        // Check if numTickets <= num seats available
        Run run = runService.getRunById(runId);
        if (run == null)
            return null;

        for (Ticket t : tickets) {
            if (t == null)
                continue;

            t.setRun(run);
            ticketRepo.save(t);
        }

        return run;

    }

    @Override
    public User assignTicketToUser(Long userId, Long ticketId) {
        // Check if user exists
        User user = userService.getUserById(userId);
        if (user == null)
            return null;

        // Check if ticket exists
        Ticket ticket = ticketService.getTicketById(ticketId);
        if (ticket == null)
            return null;

        // Check if ticket already has a user
        if (ticket.getUser() != null)
            return null;

        ticket.setUser(user);
        ticketRepo.save(ticket);

        return user;
    }

    @Override
    public User getUserOfTicket(Long id) {
        // Check if ticket exists
        Ticket ticket = getTicketById(id);
        if (ticket == null)
            return null;

        // Check if ticket has an owner
        User owner = ticket.getUser();
        if (owner == null)
            return null;

        return owner;
    }

    @Override
    public Run getRunOfTicket(Long id) {
        // Check if ticket exists
        Ticket ticket = getTicketById(id);
        if (ticket == null)
            return null;

        // Get run of ticket
        Run run = ticket.getRun();

        return run;
    }

    @Override
    public List<Seat> getSeatsWithoutTickets() {
        List<Seat> seats = seatRepo.findAll();
        if (seats == null)
            return null;

        return seats.stream().filter(s -> s.getTicket() == null).toList();
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
