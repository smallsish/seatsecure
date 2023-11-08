package com.seatsecure.backend.entities.DTOs.complex;

import java.util.List;

import com.seatsecure.backend.entities.DTOs.simple.CatDTO;
import com.seatsecure.backend.entities.DTOs.simple.EventDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EventCatsDTO{
    private EventDTO event;
    private List<CatDTO> cats;
}
