package com.seatsecure.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.seatsecure.backend.entities.Category;
import com.seatsecure.backend.entities.Event;
import com.seatsecure.backend.entities.Seat;
import com.seatsecure.backend.entities.Venue;
import com.seatsecure.backend.repositories.SeatRepository;

@Service
public class SeatServiceImpl implements SeatService {

    private SeatRepository seatRepo;
    private EventService es;
    private VenueService vs;
    private CatService cs;

    public SeatServiceImpl(SeatRepository seatRepo, EventService es, VenueService vs, CatService cs) {
        this.seatRepo = seatRepo;
        this.es = es;
        this.vs = vs;
        this.cs = cs;
    }

    @Override
    public Seat getSeatById(Long id) {
        // Check if seat exists
        Optional<Seat> seat = seatRepo.findById(id);
        if (seat.isEmpty())
            return null;

        return seat.get();

    }

    @Override
    public List<Seat> getSeatsOfVenue(Long venueId) {
        // Check if venue exists
        Venue v = vs.getVenueById(venueId);
        if (v == null) {
            return null;
        }

        // Retrieve venue's seats
        return seatRepo.getSeatsByVenue(v);
    }

    @Override
    public List<Seat> getSeatsOfCat(Long catId) {
        // Check if cat exists
        Category cat = cs.getCatById(catId);
        if (cat == null)
            return null;

        return seatRepo.getSeatsByCat(cat);
    }

    @Override
    public Venue getVenueOfSeat(Long seatId) {
        // Check if seat exists
        Seat s = getSeatById(seatId);
        if (s == null)
            return null;

        // Check if seat's venue exists
        Venue v = s.getVenue();
        if (v == null)
            return null;

        return v;
    }

    @Override
    public Venue addNewSeatsToVenue(Long id, int numSeats) {
        // Check if venue exists
        Venue v = vs.getVenueById(id);
        if (v == null) {
            return null;
        }

        // Add new seats
        for (int i = 0; i < numSeats; i++) {
            Seat newSeat = Seat.builder().venue(v).build();
            seatRepo.save(newSeat);
        }

        return v;
    }

    @Override
    public Event assignCatsToSeats(Long eventId, int startRangeIndex, int endRangeIndex, Category cat) {
        // Check if event exists
        Event e = es.getEventById(eventId);
        if (e == null)
            return null;

        // Check if event venue exists
        Venue v = es.getVenueOfEvent(eventId);
        if (v == null)
            return null;

        // Get seats of venue
        List<Seat> venueSeats = getSeatsOfVenue(v.getId());

        // Assign cats to seats in range (inclusive)
        for (int i = 0; i < venueSeats.size(); i++) {
            if (i >= startRangeIndex && i <= endRangeIndex) {
                venueSeats.get(i).setCat(cat);
            }
        }

        return e;
    }

    @Override
    public Venue deleteSeatById(Long id) {
        // Check if seat exists
        Seat s = getSeatById(id);
        if (s == null)
            return null;

        // Get venue of seat
        Venue v = getVenueOfSeat(id);
        if (v == null)
            return null;

        // Delete seat
        seatRepo.deleteById(id);

        return v;
    }

}
