package com.seatsecure.backend.entities.DTOs.mappers;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.seatsecure.backend.entities.Category;
import com.seatsecure.backend.entities.DTOs.CatDTO;

@Component
public class CatDTOmapper implements Function<Category, CatDTO>{

    @Override
    public CatDTO apply(Category cat) {
        return new CatDTO(cat.getId(), cat.getName(), cat.getDescription(), cat.getPrice());
    }
    
}
