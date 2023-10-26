package com.seatsecure.backend.entities.DTOs.mappers;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.seatsecure.backend.entities.Venue;
import com.seatsecure.backend.entities.DTOs.VenueDTO;

@Component
public class VenueDTOmapper implements Function<Venue,VenueDTO>{

    @Override
    public VenueDTO apply(Venue venue) {
        return new VenueDTO(venue.getId(), venue.getName(), venue.getAddress());
    }
    
}
