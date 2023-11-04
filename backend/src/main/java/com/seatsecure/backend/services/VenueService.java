package com.seatsecure.backend.services;

import java.util.List;

import com.seatsecure.backend.entities.Venue;
import com.seatsecure.backend.entities.DTOs.VenueEventsDTO;

public interface VenueService {

    // DTO methods
    //VenueEventsDTO getVenueEventsDTO(Long id);

    // Service methods
    List<Venue> listVenues();
    Venue getVenueById(Long id);
    Venue addVenue(Venue e);
    Venue updateVenue(Long id, Venue newVenueInfo);
    Venue deleteVenueById(Long id);

}
