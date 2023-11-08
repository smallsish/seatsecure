package com.seatsecure.backend.entities.DTOs.complex;

import com.seatsecure.backend.entities.DTOs.simple.CatDTO;
import com.seatsecure.backend.entities.DTOs.simple.RunDTO;
import com.seatsecure.backend.entities.DTOs.simple.VenueDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TicketDetailsDTO {

    private Long id;
    private Double price;
    private RunDTO run;
    private VenueDTO venue;
}
