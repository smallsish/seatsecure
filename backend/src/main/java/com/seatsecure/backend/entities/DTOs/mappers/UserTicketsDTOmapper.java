package com.seatsecure.backend.entities.DTOs.mappers;

import java.util.List;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.seatsecure.backend.entities.Ticket;
import com.seatsecure.backend.entities.User;
import com.seatsecure.backend.entities.DTOs.TicketDetailsDTO;
import com.seatsecure.backend.entities.DTOs.UserDTO;
import com.seatsecure.backend.entities.DTOs.UserTicketsDTO;
import com.seatsecure.backend.services.TicketService;

@Component
public class UserTicketsDTOmapper implements Function<User, UserTicketsDTO>{
    @Autowired
    UserDTOmapper userDTOmapper;

    @Autowired
    TicketDetailsDTOmapper ticketDetailsDTOmapper;

    @Autowired
    TicketService ts;

    @Override
    public UserTicketsDTO apply(User u) {
        UserDTO userDTO = userDTOmapper.apply(u);

        List<Ticket> tickets = ts.getTicketsOfUser(userDTO.getId());
        List<TicketDetailsDTO> ticketDTOs = tickets.stream().map(ticketDetailsDTOmapper).toList();

        return new UserTicketsDTO(userDTO, ticketDTOs);
        
    }
    
}
