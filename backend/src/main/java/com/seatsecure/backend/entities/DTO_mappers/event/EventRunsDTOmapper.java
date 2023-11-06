package com.seatsecure.backend.entities.DTO_mappers.event;

import java.util.List;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.seatsecure.backend.entities.Event;
import com.seatsecure.backend.entities.Run;
import com.seatsecure.backend.entities.DTO_mappers.run.RunDTOmapper;
import com.seatsecure.backend.entities.DTOs.event.EventDTO;
import com.seatsecure.backend.entities.DTOs.event.EventRunsDTO;
import com.seatsecure.backend.entities.DTOs.run.RunDTO;
import com.seatsecure.backend.services.RunService;

@Component
public class EventRunsDTOmapper implements Function<Event, EventRunsDTO>{

    @Autowired
    private EventDTOmapper eventDTOmapper;

    @Autowired
    private RunDTOmapper runDTOmapper;

    @Autowired
    private RunService rs;

    @Override
    public EventRunsDTO apply(Event e) {
        EventDTO eventDTO = eventDTOmapper.apply(e);

        List<Run> runs = rs.getRunsOfEvent(e.getId());
        List<RunDTO> runDTOs = runs == null ? null : runs.stream().map(runDTOmapper).toList();

        return new EventRunsDTO(eventDTO, runDTOs);
        
    }
    
}
