package com.seatsecure.backend.entities.DTOs.mappers;

import java.util.List;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.seatsecure.backend.entities.Category;
import com.seatsecure.backend.entities.Event;
import com.seatsecure.backend.entities.Run;
import com.seatsecure.backend.entities.Venue;
import com.seatsecure.backend.entities.DTOs.CatDTO;
import com.seatsecure.backend.entities.DTOs.EventDTO;
import com.seatsecure.backend.entities.DTOs.EventDetailsDTO;
import com.seatsecure.backend.entities.DTOs.RunDTO;
import com.seatsecure.backend.entities.DTOs.VenueDTO;
import com.seatsecure.backend.services.CatService;
import com.seatsecure.backend.services.EventService;
import com.seatsecure.backend.services.RunService;

@Component
public class EventDetailsDTOmapper implements Function<Event, EventDetailsDTO>{

    @Autowired
    private EventDTOmapper eventDTOmapper;

    @Autowired
    private RunDTOmapper runDTOmapper;

    @Autowired
    private CatDTOmapper catDTOmapper;

    @Autowired
    private VenueDTOmapper venueDTOmapper;

    @Autowired
    private RunService rs;

    @Autowired
    private CatService cs;

    @Autowired
    private EventService es;

    @Override
    public EventDetailsDTO apply(Event e) {
        EventDTO eventDTO = eventDTOmapper.apply(e);

        Venue venue = es.getVenueOfEvent(eventDTO.getId());
        VenueDTO venueDTO = venue == null ? null : venueDTOmapper.apply(venue);

        List<Run> runs = rs.getRunsOfEvent(e.getId());
        List<RunDTO> runDTOs = runs.stream().map(runDTOmapper).toList();

        List<Category> cats = cs.getCatsOfEvent(e.getId());
        List<CatDTO> catDTOs = cats.stream().map(catDTOmapper).toList();

        return new EventDetailsDTO(eventDTO, venueDTO, runDTOs, catDTOs);
        
    }
    
}
