package com.seatsecure.backend.entities.DTO_mappers.simple;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.seatsecure.backend.entities.Event;
import com.seatsecure.backend.entities.DTOs.simple.EventDTO;

@Component
public class EventDTOmapper implements Function<Event,EventDTO>{

    @Override
    public EventDTO apply(Event event) {
        DateFormat formatter = new SimpleDateFormat("dd-MM-YYYY HH:mm"); // For formatting date
        return new EventDTO(event.getId(), event.getName(),
        event.getDescription(), formatter.format(event.getStartDate()),
        formatter.format(event.getEndDate()));
    }
    
}
