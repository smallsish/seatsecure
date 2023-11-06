package com.seatsecure.backend.entities.DTO_mappers.ticket;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.seatsecure.backend.entities.Event;
import com.seatsecure.backend.entities.Run;
import com.seatsecure.backend.entities.Ticket;
import com.seatsecure.backend.entities.Venue;
import com.seatsecure.backend.entities.DTO_mappers.run.RunDTOmapper;
import com.seatsecure.backend.entities.DTO_mappers.venue.VenueDTOmapper;
import com.seatsecure.backend.entities.DTOs.run.RunDTO;
import com.seatsecure.backend.entities.DTOs.ticket.TicketDetailsDTO;
import com.seatsecure.backend.entities.DTOs.venue.VenueDTO;
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
    
        Run r = ts.getRunOfTicket(t.getId());
        RunDTO runDTO = r == null ? null : runDTOmapper.apply(r);
        
        Event event = runDTO == null ? null : rs.getEventOfRun(runDTO.getId());
        Venue venue = event == null ? null : es.getVenueOfEvent(event.getId());

        VenueDTO venueDTO = venue == null ? null : venueDTOmapper.apply(venue);
        return new TicketDetailsDTO(t.getId(), runDTO, venueDTO);
    }
    
}
