package com.seatsecure.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.seatsecure.backend.entities.Venue;
import com.seatsecure.backend.entities.DTOs.VenueEventsDTO;
import com.seatsecure.backend.entities.DTOs.mappers.VenueEventsDTOmapper;
import com.seatsecure.backend.repositories.VenueRepository;

@Service
public class VenueServiceImpl implements VenueService {

    private VenueRepository venueRepo;
    //private VenueEventsDTOmapper venueEventsDTOmapper;

    public VenueServiceImpl(VenueRepository venueRepo){ //}, VenueEventsDTOmapper veDTOmapper) {
        this.venueRepo = venueRepo;
        //venueEventsDTOmapper = veDTOmapper;
    }

    // DTO methods

    // @Override
    // public VenueEventsDTO getVenueEventsDTO(Long id) {
    //     Venue venue = getVenueById(id);
    //     if (venue == null) return null;

    //     return venueEventsDTOmapper.apply(venue);
    // }

    @Override
    public List<Venue> listVenues() {
        return venueRepo.findAll();
    }

    @Override
    public Venue getVenueById(Long venueId) {
        Optional<Venue> e = venueRepo.findById(venueId);
        if (e.isEmpty())
            return null;

        return e.get();
    }

    @Override
    public Venue addVenue(Venue u) {
        return venueRepo.save(u);
    }

    @Override
    public Venue updateVenue(Long id, Venue newVenueInfo) {
        // Check if venue exists
        Venue v = getVenueById(id);
        if (v == null)
            return null;

        // Update venue

        v.setName(newVenueInfo.getName());
        v.setAddress(newVenueInfo.getAddress());
        venueRepo.save(v);

        return v;
    }

    @Override
    public Venue deleteVenueById(Long venueId) {
        // Check if venue exists
        Venue venue = getVenueById(venueId);
        if (venue == null)
            return null;

        venueRepo.deleteById(venueId);
        return venue;
    }

}
