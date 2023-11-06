package com.seatsecure.backend.entities.DTOs.complex;

import com.seatsecure.backend.entities.DTOs.simple.RunDTO;
import com.seatsecure.backend.entities.DTOs.simple.VenueDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TicketDetailsDTO {
    // Given ticket, get run and location

    private Long id;
    private RunDTO run;
    private VenueDTO venue;
}
