package com.seatsecure.backend.services;

import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.seatsecure.backend.entities.Event;
import com.seatsecure.backend.entities.EventVenueDTO;

@Service
public class EventVenueDTOmapper implements Function<Event,EventVenueDTO>{
    @Override
    public EventVenueDTO apply(Event event){
        return new EventVenueDTO(event.getName(),event.getStartDate(),event.getEndDate(),event.getVenue().getName());
    }

}
