package com.seatsecure.backend.entities.DTO_mappers.venue;

import java.util.List;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.seatsecure.backend.entities.Event;
import com.seatsecure.backend.entities.Venue;
import com.seatsecure.backend.entities.DTO_mappers.event.EventDTOmapper;
import com.seatsecure.backend.entities.DTOs.event.EventDTO;
import com.seatsecure.backend.entities.DTOs.venue.VenueDTO;
import com.seatsecure.backend.entities.DTOs.venue.VenueEventsDTO;
import com.seatsecure.backend.services.EventService;

@Component
public class VenueEventsDTOmapper implements Function<Venue,VenueEventsDTO>{

    @Autowired
    private EventService es;

    @Autowired
    private VenueDTOmapper venueDTOmapper;

    @Autowired
    private EventDTOmapper eventDTOmapper;

    @Override
    public VenueEventsDTO apply(Venue venue) {
        VenueDTO venueDTO = venueDTOmapper.apply(venue);
        List<Event> events = es.getEventsOfVenue(venue.getId());
        List<EventDTO> eventDTOs = events == null ? null : events.stream().map(eventDTOmapper).toList();

        return new VenueEventsDTO(venueDTO, eventDTOs);
    }
    
}
