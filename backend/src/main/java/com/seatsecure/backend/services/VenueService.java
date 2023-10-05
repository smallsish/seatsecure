package com.seatsecure.backend.services;

import java.util.List;

import com.seatsecure.backend.entities.Venue;

public interface VenueService {
    List<Venue> listVenues();
    Venue getVenueById(Long id);
    Venue addVenue(Venue e);
    Venue updateVenue(Long id, Venue newVenueInfo);
    Venue deleteVenueById(Long id);
}
