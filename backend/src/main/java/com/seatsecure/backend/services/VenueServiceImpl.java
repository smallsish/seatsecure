package com.seatsecure.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.seatsecure.backend.entities.Venue;
import com.seatsecure.backend.repositories.VenueRepository;

@Service
public class VenueServiceImpl implements VenueService {

    private VenueRepository venueRepo;

    public VenueServiceImpl(VenueRepository venueRepo){
        this.venueRepo = venueRepo;

    }

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
    public Venue addVenue(Venue venueInfo) {
        Venue v = Venue.builder().name(venueInfo.getName()).address(venueInfo.getAddress()).build();
        
        return venueRepo.save(v);
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


        return venueRepo.save(v);
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
