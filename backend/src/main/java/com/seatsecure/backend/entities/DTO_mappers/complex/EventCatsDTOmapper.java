package com.seatsecure.backend.entities.DTO_mappers.complex;

import java.util.List;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.seatsecure.backend.entities.Category;
import com.seatsecure.backend.entities.Event;
import com.seatsecure.backend.entities.DTO_mappers.simple.CatDTOmapper;
import com.seatsecure.backend.entities.DTO_mappers.simple.EventDTOmapper;
import com.seatsecure.backend.entities.DTOs.complex.EventCatsDTO;
import com.seatsecure.backend.entities.DTOs.simple.CatDTO;
import com.seatsecure.backend.entities.DTOs.simple.EventDTO;
import com.seatsecure.backend.services.CatService;

@Component
public class EventCatsDTOmapper implements Function<Event, EventCatsDTO>{

    @Autowired
    private EventDTOmapper eventDTOmapper;

    @Autowired
    private CatDTOmapper catDTOmapper;

    @Autowired
    private CatService cs;

    @Override
    public EventCatsDTO apply(Event e) {
        EventDTO eventDTO = eventDTOmapper.apply(e);

        List<Category> cats = cs.getCatsOfEvent(e.getId());
        List<CatDTO> catDTOs = cats == null ? null : cats.stream().map(catDTOmapper).toList();

        return new EventCatsDTO(eventDTO, catDTOs);
        
    }
    
}
