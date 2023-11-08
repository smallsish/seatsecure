package com.seatsecure.backend.services;

import java.util.List;

import com.seatsecure.backend.entities.Seat;
import com.seatsecure.backend.entities.Venue;

public interface SeatService {

    Seat getSeatById(Long id);
    List<Seat> getSeatsOfVenue(Long venueId);
    List<Seat> getSeatsOfCat(Long catId);
    Venue getVenueOfSeat(Long seatId);

    List<Seat> addNewSeatsToVenue(Long id, int numSeats);
    List<Seat> assignCatToSeats(Long catId, Long startSeatId, Long endSeatId);

    Seat deleteSeatById(Long seatId);


}
