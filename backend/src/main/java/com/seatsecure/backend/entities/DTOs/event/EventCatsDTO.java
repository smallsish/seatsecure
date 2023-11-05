package com.seatsecure.backend.entities.DTOs.event;

import java.util.List;

import com.seatsecure.backend.entities.DTOs.cat.CatDTO;

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
