package com.seatsecure.backend.entities.DTO_mappers.complex;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.seatsecure.backend.entities.Event;
import com.seatsecure.backend.entities.Venue;
import com.seatsecure.backend.entities.DTO_mappers.simple.EventDTOmapper;
import com.seatsecure.backend.entities.DTO_mappers.simple.VenueDTOmapper;
import com.seatsecure.backend.entities.DTOs.complex.VenueEventDTO;
import com.seatsecure.backend.entities.DTOs.simple.EventDTO;
import com.seatsecure.backend.entities.DTOs.simple.VenueDTO;
import com.seatsecure.backend.services.EventService;

@Component
public class VenueEventDTOmapper implements Function<Venue,VenueEventDTO>{

    @Autowired
    private EventService es;

    @Autowired
    private VenueDTOmapper venueDTOmapper;

    @Autowired
    private EventDTOmapper eventDTOmapper;

    @Override
    public VenueEventDTO apply(Venue venue) {
        VenueDTO venueDTO = venueDTOmapper.apply(venue);
        Event event = es.getEventAtVenue(venue.getId());
        EventDTO eventDTO = event == null ? null : eventDTOmapper.apply(event);

        return new VenueEventDTO(venueDTO, eventDTO);
    }
    
}
