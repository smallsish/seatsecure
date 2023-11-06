package com.seatsecure.backend.entities.DTO_mappers.complex;

import java.util.List;
import java.util.function.Function;

import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.seatsecure.backend.entities.Category;
import com.seatsecure.backend.entities.Event;
import com.seatsecure.backend.entities.Run;
import com.seatsecure.backend.entities.Venue;
import com.seatsecure.backend.entities.DTO_mappers.simple.CatDTOmapper;
import com.seatsecure.backend.entities.DTO_mappers.simple.EventDTOmapper;
import com.seatsecure.backend.entities.DTO_mappers.simple.RunDTOmapper;
import com.seatsecure.backend.entities.DTO_mappers.simple.VenueDTOmapper;
import com.seatsecure.backend.entities.DTOs.complex.EventDetailsDTO;
import com.seatsecure.backend.entities.DTOs.simple.CatDTO;
import com.seatsecure.backend.entities.DTOs.simple.EventDTO;
import com.seatsecure.backend.entities.DTOs.simple.RunDTO;
import com.seatsecure.backend.entities.DTOs.simple.VenueDTO;
import com.seatsecure.backend.services.CatService;
import com.seatsecure.backend.services.EventService;
import com.seatsecure.backend.services.RunService;

@Component
public class EventDetailsDTOmapper implements Function<Event, EventDetailsDTO>{

    @Lazy
    @Autowired
    private EventDTOmapper eventDTOmapper;

    @Lazy
    @Autowired
    private RunDTOmapper runDTOmapper;

    @Lazy
    @Autowired
    private CatDTOmapper catDTOmapper;

    @Lazy
    @Autowired
    private VenueDTOmapper venueDTOmapper;

    @Lazy
    @Autowired
    private RunService rs;

    @Lazy
    @Autowired
    private CatService cs;

    @Lazy
    @Autowired
    private EventService es;

    @Override
    public EventDetailsDTO apply(Event e) {
        EventDTO eventDTO = eventDTOmapper.apply(e);

        Venue venue = es.getVenueOfEvent(eventDTO.getId());
        VenueDTO venueDTO = venue == null ? null : venueDTOmapper.apply(venue);

        List<Run> runs = rs.getRunsOfEvent(e.getId());
        List<RunDTO> runDTOs = runs == null ? null : runs.stream().map(runDTOmapper).toList();

        List<Category> cats = cs.getCatsOfEvent(e.getId());
        List<CatDTO> catDTOs = cats == null ? null : cats.stream().map(catDTOmapper).toList();

        return new EventDetailsDTO(eventDTO, venueDTO, runDTOs, catDTOs);
        
    }
    
}
