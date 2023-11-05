package com.seatsecure.backend.entities.DTOs.ticket;

import com.seatsecure.backend.entities.DTOs.run.RunDTO;
import com.seatsecure.backend.entities.DTOs.venue.VenueDTO;

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
