package com.seatsecure.backend.entities.DTO_mappers.event;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.seatsecure.backend.entities.Event;
import com.seatsecure.backend.entities.Venue;
import com.seatsecure.backend.entities.DTO_mappers.venue.VenueDTOmapper;
import com.seatsecure.backend.entities.DTOs.event.EventDTO;
import com.seatsecure.backend.entities.DTOs.event.EventVenueDTO;
import com.seatsecure.backend.entities.DTOs.venue.VenueDTO;
import com.seatsecure.backend.services.EventService;

@Component
public class EventVenueDTOmapper implements Function<Event, EventVenueDTO>{

    @Autowired
    private EventDTOmapper eventDTOmapper;

    @Autowired
    private VenueDTOmapper venueDTOmapper;

    @Autowired
    private EventService es;

    @Override
    public EventVenueDTO apply(Event e) {
        EventDTO eventDTO = eventDTOmapper.apply(e);

        Venue venue = es.getVenueOfEvent(eventDTO.getId());
        VenueDTO venueDTO = venue == null ? null : venueDTOmapper.apply(venue);

        return new EventVenueDTO(eventDTO, venueDTO);
        
    }
    
}
