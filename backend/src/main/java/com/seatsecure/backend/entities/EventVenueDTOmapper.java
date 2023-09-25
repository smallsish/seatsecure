package com.seatsecure.backend.entities;

import java.util.function.Function;

import org.springframework.stereotype.Service;

@Service
public class EventVenueDTOmapper implements Function<Event,EventVenueDTO>{
    @Override
    public EventVenueDTO apply(Event event){
        return new EventVenueDTO(event.getName(),event.getStartDate(),event.getEndDate(),event.getVenue().getName());
    }

}
