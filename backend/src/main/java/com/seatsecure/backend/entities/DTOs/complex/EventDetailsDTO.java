package com.seatsecure.backend.entities.DTOs.complex;

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
    private EventDTO event;
    private VenueDTO venue;
    private List<RunDTO> runs;
    private List<CatDTO> cats;
}
