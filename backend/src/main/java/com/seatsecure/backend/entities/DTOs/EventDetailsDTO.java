package com.seatsecure.backend.entities.DTOs;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EventDetailsDTO{
    // Given an event, list its runs and cats
    
    private EventDTO event;
    private VenueDTO venue;
    private List<RunDTO> runs;
    private List<CatDTO> cats;
}
