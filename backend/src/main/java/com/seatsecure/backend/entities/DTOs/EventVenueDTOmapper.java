package com.seatsecure.backend.entities.DTOs;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.seatsecure.backend.entities.Event;

@Service
public class EventVenueDTOmapper implements Function<Event,EventVenueDTO>{
    @Override
    public EventVenueDTO apply(Event event){
        DateFormat formatter = new SimpleDateFormat("dd-MM-YYYY HH:mm");
        return new EventVenueDTO(event.getName(),formatter.format(event.getStartDate()),formatter.format(event.getEndDate()),event.getVenue().getName());
    }

}
