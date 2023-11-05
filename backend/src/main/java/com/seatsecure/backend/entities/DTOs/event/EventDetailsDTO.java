package com.seatsecure.backend.entities.DTOs.event;

import java.util.List;

import com.seatsecure.backend.entities.DTOs.cat.CatDTO;
import com.seatsecure.backend.entities.DTOs.run.RunDTO;
import com.seatsecure.backend.entities.DTOs.venue.VenueDTO;

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
