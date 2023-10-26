package com.seatsecure.backend.entities.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CatDTO {
    private Long id;
    private String name;
    private String description;
    private double price;
}