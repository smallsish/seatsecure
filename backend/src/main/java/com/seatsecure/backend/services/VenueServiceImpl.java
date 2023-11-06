package com.seatsecure.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.seatsecure.backend.entities.Venue;
import com.seatsecure.backend.exceptions.venue.VenueNotFoundException;
import com.seatsecure.backend.repositories.VenueRepository;

@Service
public class VenueServiceImpl implements VenueService {

    private VenueRepository venueRepo;

    public VenueServiceImpl(VenueRepository vr){
        venueRepo = vr;

    }

    /** 
     * Get all Venues
     * 
     * @return A list of all Venues
     */
    @Override
    public List<Venue> listVenues() {
        return venueRepo.findAll();
    }

    /** 
     * Get the Venue with the specified id
     * 
     * @param venueId
     * @return The Venue with the specified id
     * @throws VenueNotFoundException If a Venue with the specified id does not exist
     */
    @Override
    public Venue getVenueById(Long venueId) {
        Optional<Venue> e = venueRepo.findById(venueId);
        if (e.isEmpty()) {
            throw new VenueNotFoundException(venueId);
        }

        return e.get();
    }

    /** 
     * Add a new Venue
     * 
     * @param venueInfo
     * @return The newly added Venue
     */
    @Override
    public Venue addVenue(Venue venueInfo) {
        Venue v = Venue.builder().name(venueInfo.getName()).address(venueInfo.getAddress()).build();
        
        return venueRepo.save(v);
    }

    /** 
     * Update the Venue with the specified id with new info
     * 
     * @param venueId
     * @param venueInfo
     * @return The updated Venue
     * @throws VenueNotFoundException If a Venue with the specified id does not exist
     */
    @Override
    public Venue updateVenue(Long id, Venue venueInfo) {
        // Check if venue exists
        Venue v = getVenueById(id);

        // Update venue
        v.setName(venueInfo.getName());
        v.setAddress(venueInfo.getAddress());

        return venueRepo.save(v);
    }

    /** 
     * Delete the Venue with the specified id
     * 
     * @param venueId
     * @return The deleted Venue with the specified id
     * @throws VenueNotFoundException If a Venue with the specified id does not exist
     */
    @Override
    public Venue deleteVenueById(Long venueId) {
        // Check if venue exists
        Venue venue = getVenueById(venueId);

        // Delete venue
        venueRepo.deleteById(venueId);
        return venue;
    }

}
