package com.seatsecure.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.seatsecure.backend.entities.Seat;
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
    public Venue getVenueById(Long venueId){
        Optional<Venue> e = venueRepo.findById(venueId);
        if (e.isEmpty()) return null;

        return e.get();
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

    @Override
    public Venue deleteVenueById(Long venueId){
        Venue venue = getVenueById(venueId);
        if (venue == null) return null;

        venueRepo.deleteById(venueId);
        return venue;
    }

    @Override
    public Seat getSeatByNum(Long venueId, Integer seatNum) {
        Venue venue = getVenueById(venueId);
        if (venue == null) return null;

        List<Seat> venueSeats = venue.getSeats();

        // Find the seat with the specified id
        for (Seat s : venueSeats) {
            if ((long) s.getSeatNum() == seatNum) return s;
        }
        return null;
    }

    @Override
    public Venue addSeatsToVenue(Long id, int numSeats) {
        Venue venue = getVenueById(id);
        if (venue == null) return null;

        List<Seat> venueSeats = venue.getSeats();

        for (int i = 0; i < numSeats; i++) venueSeats.add(new Seat());
        
        return venue;
    }

}
