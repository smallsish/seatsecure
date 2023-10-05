package com.seatsecure.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seatsecure.backend.entities.Venue;
import com.seatsecure.backend.repositories.VenueRepository;

@Service
public class VenueServiceImpl implements VenueService {
   
    private VenueRepository venueRepo;

    @Autowired
    public VenueServiceImpl(VenueRepository venueRepo){
        this.venueRepo = venueRepo;
    }

    @Override
    public List<Venue> listVenues() {
        return venueRepo.findAll();
    }

    
    @Override
    public Venue getVenueById(Long venueId){
        // Using Java Optional, as "findById" of Spring JPA returns an Optional object
        // Optional forces developers to explicitly handle the case of non-existent values
        // Here is an implementation using lambda expression to extract the value from Optional<Book>
        Optional<Venue> e = venueRepo.findById(venueId);
        if (e.isEmpty()) return null;

        return e.get();
        // return venueRepo.findById(venueId).map(e -> {
        //     return e;
        // }).orElse(null);
    }
    
    @Override
    public Venue addVenue(Venue u) {
        return venueRepo.save(u);
    }
    
    @Override
    public Venue updateVenue(Long id, Venue newVenueInfo){
        Optional<Venue> venue = venueRepo.findById(id);
        if (venue.isEmpty()) return null; // Venue not found

        Venue e = venue.get();
        e.setName(newVenueInfo.getName());
        e.setAddress(newVenueInfo.getAddress());
        venueRepo.save(e);

        return e;
    }

    /**
     * Remove a venue with the given id
     * Spring Data JPA does not return a value for delete operation
     */
    @Override
    public Venue deleteVenueById(Long venueId){
        Venue venue = getVenueById(venueId);
        if (venue == null) {
            return null;
        }
        venueRepo.deleteById(venueId);
        return venue;
    }
}
