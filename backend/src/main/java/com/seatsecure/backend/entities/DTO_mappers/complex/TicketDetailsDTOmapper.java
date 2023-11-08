package com.seatsecure.backend.entities.DTO_mappers.complex;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.seatsecure.backend.entities.Category;
import com.seatsecure.backend.entities.Event;
import com.seatsecure.backend.entities.Run;
import com.seatsecure.backend.entities.Ticket;
import com.seatsecure.backend.entities.Venue;
import com.seatsecure.backend.entities.DTO_mappers.simple.CatDTOmapper;
import com.seatsecure.backend.entities.DTO_mappers.simple.RunDTOmapper;
import com.seatsecure.backend.entities.DTO_mappers.simple.VenueDTOmapper;
import com.seatsecure.backend.entities.DTOs.complex.TicketDetailsDTO;
import com.seatsecure.backend.entities.DTOs.simple.CatDTO;
import com.seatsecure.backend.entities.DTOs.simple.RunDTO;
import com.seatsecure.backend.entities.DTOs.simple.VenueDTO;
import com.seatsecure.backend.services.EventService;
import com.seatsecure.backend.services.RunService;
import com.seatsecure.backend.services.TicketAccessorService;

@Lazy
@Component
public class TicketDetailsDTOmapper implements Function<Ticket, TicketDetailsDTO>{

    @Autowired
    private RunDTOmapper runDTOmapper;

    @Autowired
    private VenueDTOmapper venueDTOmapper;

    @Autowired
    private CatDTOmapper catDTOmapper;

    @Autowired
    private TicketAccessorService ts;

    @Autowired
    private RunService rs;

    @Autowired
    private EventService es;

    @Override
    public TicketDetailsDTO apply(Ticket t) {
    
        Run r = ts.getRunOfTicket(t.getId());
        RunDTO runDTO = r == null ? null : runDTOmapper.apply(r);

        Category c = ts.getCatOfTicket(t.getId());
        
        Event event = runDTO == null ? null : rs.getEventOfRun(runDTO.getId());
        Venue venue = event == null ? null : es.getVenueOfEvent(event.getId());

        VenueDTO venueDTO = venue == null ? null : venueDTOmapper.apply(venue);
        return new TicketDetailsDTO(t.getId(), c == null ? null : c.getPrice(), runDTO, venueDTO);
    }
    
}
