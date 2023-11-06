package com.seatsecure.backend.entities.DTO_mappers.complex;

import java.util.List;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.seatsecure.backend.entities.Run;
import com.seatsecure.backend.entities.Ticket;
import com.seatsecure.backend.entities.DTO_mappers.simple.RunDTOmapper;
import com.seatsecure.backend.entities.DTOs.complex.RunTicketsDTO;
import com.seatsecure.backend.entities.DTOs.complex.TicketDetailsDTO;
import com.seatsecure.backend.entities.DTOs.simple.RunDTO;
import com.seatsecure.backend.services.TicketService;

@Component
public class RunTicketsDTOmapper implements Function<Run, RunTicketsDTO>{

    @Autowired
    private RunDTOmapper runDTOmapper;

    @Autowired
    private TicketDetailsDTOmapper ticketDetailsDTOmapper;

    @Autowired
    private TicketService ticketService;

    @Override
    public RunTicketsDTO apply(Run r) {
        RunDTO runDTO = runDTOmapper.apply(r);

        List<Ticket> tickets = runDTO == null ? null : ticketService.getTicketsOfRun(runDTO.getId());
        List<TicketDetailsDTO> ticketDTOs = tickets == null ? null : tickets.stream().map(ticketDetailsDTOmapper).toList();

        return new RunTicketsDTO(runDTO, ticketDTOs);
        
    }
    
}
