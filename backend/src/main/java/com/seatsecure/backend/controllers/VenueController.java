package com.seatsecure.backend.controllers;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.seatsecure.backend.entities.Venue;
import com.seatsecure.backend.entities.DTO_mappers.complex.VenueEventDTOmapper;
import com.seatsecure.backend.entities.DTO_mappers.simple.VenueDTOmapper;
import com.seatsecure.backend.entities.DTOs.complex.VenueEventDTO;
import com.seatsecure.backend.entities.DTOs.simple.VenueDTO;
import com.seatsecure.backend.exceptions.creation.VenueCreationException;
import com.seatsecure.backend.exceptions.not_found.VenueNotFoundException;
import com.seatsecure.backend.services.VenueService;

@RequestMapping("/api/v1")
@RestController
public class VenueController {
    private VenueService venueService;
    private VenueDTOmapper venueDTOmapper;
    private VenueEventDTOmapper venueEventDTOmapper;

    public VenueController(VenueService vs, VenueDTOmapper vDTOmapper, VenueEventDTOmapper veDTOmapper) {
        venueService = vs;
        venueDTOmapper = vDTOmapper;
        venueEventDTOmapper = veDTOmapper;
    }

    /**
     * List all existing venues
     * @return list of all venues
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/venues")
    public List<VenueDTO> getVenues(){
        List<Venue> venues = venueService.listVenues();
        return venues.stream().map(venueDTOmapper).toList();
    }

    /**
     * Search for venue with the given id
     * If there is no venue with the given "id", throw a VenueNotFoundException
     * @param id
     * @return Venue with the given id
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/venues/{id}")
    public VenueDTO getVenue(@PathVariable Long id){
        Venue venue = venueService.getVenueById(id);
        if(venue == null) throw new VenueNotFoundException(id);
        
        return venueDTOmapper.apply(venue);
    }

    /**
     * Add a new venue with POST request to "/venues"
     * @param venue
     * @return The new venue that was added
    */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/venues")
    @PreAuthorize("hasAuthority('admin:create')")
    public VenueDTO addVenue(@Valid @RequestBody Venue venue) {
        Venue v = venueService.addVenue(venue);
        if (v == null) throw new VenueCreationException();
        
        return venueDTOmapper.apply(v);
    }


    /**
     * Update an venue with new info
     * If there is no venue with the given "id", throw an VenueNotFoundException
     * @param id
     * @param newVenueInfo
     * @return the updated venue
     */
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/venues/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    public VenueDTO updateVenue(@PathVariable Long id, @Valid @RequestBody Venue newVenueInfo){
        Venue venue = venueService.updateVenue(id, newVenueInfo);
        if(venue == null) throw new VenueNotFoundException(id);
        
        return venueDTOmapper.apply(venue);
    }

    /**
     * Remove a venue with the DELETE request to "/venue/{id}"
     * If there is no venue with the given "id", throw an VenueNotFoundException
     * @param id
     */
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/venues/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public VenueDTO deleteVenue(@PathVariable Long id){
        Venue venue = venueService.deleteVenueById(id);
        if(venue == null) throw new VenueNotFoundException(id);

        return venueDTOmapper.apply(venue);
    }

    /**
     * List events at the venue
     * If there is no venue with the given "id", throw an VenueNotFoundException
     * @param id
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/venues/{id}/events")
    public VenueEventDTO getEventsAtVenue(@PathVariable Long id){
        Venue venue = venueService.getVenueById(id);
        if (venue == null) throw new VenueNotFoundException(id);

        return venueEventDTOmapper.apply(venue);
    }
}
