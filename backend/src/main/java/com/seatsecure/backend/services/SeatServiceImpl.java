package com.seatsecure.backend.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.seatsecure.backend.entities.Category;
import com.seatsecure.backend.entities.Seat;
import com.seatsecure.backend.entities.Venue;
import com.seatsecure.backend.exceptions.seat.SeatNotFoundException;
import com.seatsecure.backend.exceptions.venue.NullVenueException;
import com.seatsecure.backend.repositories.SeatRepository;

@Service
public class SeatServiceImpl implements SeatService {

    // Due to the large number of dependencies, lazy setter injection is used
    // (setter injectors are below)
    private SeatRepository seatRepo;
    private EventService eventService;
    private VenueService venueService;
    private CatService catService;

    /**
     * Get a Seat by id
     * 
     * @param seatId
     * @return The Seat with the specified id
     * @throws SeatNotFoundException If the Seat with the specified id does not
     *                               exist
     */
    @Override
    public Seat getSeatById(Long seatId) {
        // Check if seat exists
        Optional<Seat> seat = seatRepo.findById(seatId);
        if (seat.isEmpty()) {
            throw new SeatNotFoundException(seatId);
        }

        return seat.get();

    }

    /**
     * Get all Seats at a specified Venue
     * 
     * @param venueId
     * @return A list of the Seats at the Venue with the specified id
     * @throws VenueNotFound If the Venue with the specified id does not exist
     */
    @Override
    public List<Seat> getSeatsOfVenue(Long venueId) {
        // Check if venue exists
        Venue v = venueService.getVenueById(venueId);

        // Retrieve venue's seats
        return seatRepo.getSeatsByVenue(v);
    }

    /**
     * Get all Seats of a specified Category
     * 
     * @param catId
     * @return A list of the Seats with a Category of a specified id
     * @throws CatNotFoundException If the Venue with the specified id does not
     *                              exist
     */
    @Override
    public List<Seat> getSeatsOfCat(Long catId) {
        // Check if cat exists
        Category cat = catService.getCatById(catId);

        return seatRepo.getSeatsByCat(cat);
    }

    /**
     * Get the Venue of a Seat
     * 
     * @param seatId
     * @return The Venue of a Seat with the specified id
     * @throws SeatNotFoundException If the Seat with the specified id does not
     *                               exist
     * @throws NullVenueException    If the Seat does not have a Venue it is
     *                               associated with
     */
    @Override
    public Venue getVenueOfSeat(Long seatId) {
        // Check if seat exists
        Seat s = getSeatById(seatId);

        // Check if seat's venue exists
        Venue v = s.getVenue();
        if (v == null) {
            throw new NullVenueException();
        }

        return v;
    }

    /**
     * Add a number of new Seats to a Venue
     * 
     * @param venueId
     * @param numSeats
     * @return The list of new Seats added to the Venue
     * @throws VenueNotFoundException If a Venue with the specified id does not
     *                                exist
     */
    @Override
    public List<Seat> addNewSeatsToVenue(Long id, int numSeats) {
        // Check if venue exists
        Venue v = venueService.getVenueById(id);

        List<Seat> seatList = new ArrayList<>();
        // Add new seats
        for (int i = 0; i < numSeats; i++) {
            Seat newSeat = Seat.builder().venue(v).build();
            seatList.add(seatRepo.save(newSeat));
        }

        return seatList;
    }

    /**
     * Assign a Category to Seats of id startRangeIndex to endRangeIndex
     * Overwrites existing property if set
     * 
     * @param venueId
     * @param catId
     * @param startRangeIndex
     * @param endRangeIndex
     * @return A list of the updated Seats
     * @throws CatNotFoundException  If a Category with the specified id does not
     *                               exist
     * @throws SeatNotFoundException If a Seat with the specified id does not exist
     */
    @Override
    public List<Seat> assignCatToSeats(Long catId, Long startRangeIndex, Long endRangeIndex) {

        // Check if cat exists
        Category c = catService.getCatById(catId);

        List<Seat> updatedSeats = new ArrayList<>();
        // Assign cats to seats in range (inclusive)
        for (long i = startRangeIndex; i <= endRangeIndex; i++) {

            // Update the Cat property of the Seat
            Seat s = getSeatById(i);
            
            s.setCat(c);
            s = seatRepo.save(s);
            updatedSeats.add(s);
        }

        return updatedSeats;
    }

    /**
     * Delete a Seat with the specified id
     * 
     * @param seatId
     * @return The deleted Seat
     * @throws SeatNotFoundException  If a Seat with the specified id does not exist
     */
    @Override
    public Seat deleteSeatById(Long seatId) {
        // Check if seat exists
        Seat s = getSeatById(seatId);

        // Delete seat
        seatRepo.deleteById(seatId);
        return s;
    }

    /*
     * SETTER INJECTORS
     */

    @Lazy
    @Autowired
    public void injectSeatRepo(SeatRepository sr) {
        seatRepo = sr;
    }

    @Lazy
    @Autowired
    public void injectEventService(EventService es) {
        eventService = es;
    }

    @Lazy
    @Autowired
    public void injectVenueService(VenueService vs) {
        venueService = vs;
    }

    @Lazy
    @Autowired
    public void injectCatService(CatService cs) {
        catService = cs;
    }

}
