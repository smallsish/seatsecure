package com.seatsecure.backend.entities.DTOs.complex;

import java.util.List;

import com.seatsecure.backend.entities.DTOs.simple.RunDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RunTicketsDTO{
    private RunDTO run;
    private List<TicketDetailsDTO> tickets;
}
