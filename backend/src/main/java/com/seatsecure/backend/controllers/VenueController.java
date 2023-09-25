package com.seatsecure.backend.controllers;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.seatsecure.backend.entities.Venue;
import com.seatsecure.backend.entities.User;
import com.seatsecure.backend.exceptions.VenueCreationError;
import com.seatsecure.backend.exceptions.VenueNotFoundException;
import com.seatsecure.backend.exceptions.UserNotFoundException;
import com.seatsecure.backend.security.auth.AuthenticationResponse;
import com.seatsecure.backend.security.auth.AuthenticationService;
import com.seatsecure.backend.security.auth.RegisterRequest;
import com.seatsecure.backend.services.VenueService;
import com.seatsecure.backend.services.UserService;

@RequestMapping("/api/v1")
@RestController
public class VenueController {
    private VenueService venueService;
    private AuthenticationService authService;

    @Autowired
    public VenueController(VenueService es, AuthenticationService as){
        venueService = es;
        authService = as;
    }

    /**
     * List all existing venues
     * @return list of all venues
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/venues")
    public List<Venue> getVenues(){
        return venueService.listVenues();
    }

    /**
     * Search for venue with the given id
     * If there is no venue with the given "id", throw a VenueNotFoundException
     * @param id
     * @return Venue with the given id
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/venues/{id}")
    public Venue getVenue(@PathVariable Long id){
        Venue venue = venueService.getVenueById(id);
        if(venue == null) throw new VenueNotFoundException(id);
        
        return venue;
    }

    /**
     * Add a new venue with POST request to "/venues"
     * @param venue
     * @return The new venue that was added
     
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/venues")
    public ResponseEntity<AuthenticationResponse> addVenue(
        @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(authService.register(request));
    }*/

    /**
     * Add a new venue with POST request to "/venues"
     * @param venue
     * @return The new venue that was added
    */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/venues")
    public Venue addVenue(@Valid @RequestBody Venue venue) {
        Venue e = venueService.addVenue(venue);
        
        if (e == null) throw new VenueCreationError();
        
        return venue;
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
    public Venue updateVenue(@PathVariable Long id, @Valid @RequestBody Venue newVenueInfo){
        Venue venue = venueService.updateVenue(id, newVenueInfo);
        if(venue == null) throw new VenueNotFoundException(id);
        
        return venue;
    }

    /**
     * Remove a venue with the DELETE request to "/venue/{id}"
     * If there is no venue with the given "id", throw an VenueNotFoundException
     * @param id
     */
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/venues/{id}")
    public Venue deleteVenue(@PathVariable Long id){

        Venue venue = venueService.deleteVenueById(id);
        if(venue == null) throw new VenueNotFoundException(id);

        return venue;
    }
}
