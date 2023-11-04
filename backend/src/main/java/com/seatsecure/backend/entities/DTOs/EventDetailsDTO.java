package com.seatsecure.backend.entities.DTOs;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EventDetailsDTO{
    private EventDTO event;
    private VenueDTO venue;
    private List<RunDTO> runs;
    private List<CatDTO> cats;
}
