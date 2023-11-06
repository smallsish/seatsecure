package com.seatsecure.backend.services;

import java.util.List;

import com.seatsecure.backend.entities.Category;
import com.seatsecure.backend.entities.Event;
import com.seatsecure.backend.entities.Seat;
import com.seatsecure.backend.entities.Venue;

public interface SeatService {

    Seat getSeatById(Long id);
    List<Seat> getSeatsOfVenue(Long venueId);
    List<Seat> getSeatsOfCat(Long catId);
    Venue getVenueOfSeat(Long seatId);

    Venue addNewSeatsToVenue(Long id, int numSeats);
    List<Seat> assignCatToSeats(Long catId, Long startRangeIndex, Long endRangeIndex);

    Seat deleteSeatById(Long seatId);


}
