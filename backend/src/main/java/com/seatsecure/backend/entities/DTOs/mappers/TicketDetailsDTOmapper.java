package com.seatsecure.backend.entities.DTOs.mappers;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.seatsecure.backend.entities.Event;
import com.seatsecure.backend.entities.Ticket;
import com.seatsecure.backend.entities.Venue;
import com.seatsecure.backend.entities.DTOs.RunDTO;
import com.seatsecure.backend.entities.DTOs.TicketDetailsDTO;
import com.seatsecure.backend.entities.DTOs.VenueDTO;
import com.seatsecure.backend.services.EventService;
import com.seatsecure.backend.services.RunService;
import com.seatsecure.backend.services.TicketService;

@Component
public class TicketDetailsDTOmapper implements Function<Ticket, TicketDetailsDTO>{

    @Autowired
    private RunDTOmapper runDTOmapper;

    @Autowired
    private VenueDTOmapper venueDTOmapper;

    @Autowired
    private TicketService ts;

    @Autowired
    private RunService rs;

    @Autowired
    private EventService es;

    @Override
    public TicketDetailsDTO apply(Ticket t) {
    
        RunDTO runDTO = runDTOmapper.apply(ts.getRunOfTicket(t.getId()));
        
        Event event = rs.getEventOfRun(runDTO.getId());
        Venue venue = es.getVenueOfEvent(event.getId());

        VenueDTO venueDTO = venueDTOmapper.apply(venue);
        return new TicketDetailsDTO(t.getId(), runDTO, venueDTO);
    }
    
}
