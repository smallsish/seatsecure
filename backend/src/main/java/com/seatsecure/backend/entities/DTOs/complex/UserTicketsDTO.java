package com.seatsecure.backend.entities.DTOs.complex;

import java.util.List;

import com.seatsecure.backend.entities.DTOs.simple.UserDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserTicketsDTO {
    // Given user, get tickets purchased

    private UserDTO user;
    private List<TicketDetailsDTO> tickets;
}
