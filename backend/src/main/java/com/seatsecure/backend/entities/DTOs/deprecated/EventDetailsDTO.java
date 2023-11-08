package com.seatsecure.backend.entities.DTOs.deprecated;

import java.util.List;

import com.seatsecure.backend.entities.DTOs.simple.CatDTO;
import com.seatsecure.backend.entities.DTOs.simple.EventDTO;
import com.seatsecure.backend.entities.DTOs.simple.RunDTO;
import com.seatsecure.backend.entities.DTOs.simple.VenueDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EventDetailsDTO{
    // DEPRECATED AS IT IS TOO HEAVY
    // AND THE MAPPER HAS WAY TOO MANY DEPENDENCIES
    // USE EVENTCATS, EVENTRUNS AND EVENTVENUE INSTEAD
    
    private EventDTO event;
    private VenueDTO venue;
    private List<RunDTO> runs;
    private List<CatDTO> cats;
}
