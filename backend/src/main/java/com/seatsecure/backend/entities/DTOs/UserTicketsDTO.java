package com.seatsecure.backend.entities.DTOs;

import java.util.List;


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
