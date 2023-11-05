package com.seatsecure.backend.entities.DTOs.cat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CatDTO {
    private Long id;
    private String name;
    private String description;
    private double price;
}
