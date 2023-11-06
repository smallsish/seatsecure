package com.seatsecure.backend.entities.DTO_mappers.user;

import java.util.List;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.seatsecure.backend.entities.Ticket;
import com.seatsecure.backend.entities.User;
import com.seatsecure.backend.entities.DTO_mappers.ticket.TicketDetailsDTOmapper;
import com.seatsecure.backend.entities.DTOs.ticket.TicketDetailsDTO;
import com.seatsecure.backend.entities.DTOs.user.UserDTO;
import com.seatsecure.backend.entities.DTOs.user.UserTicketsDTO;
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
        List<TicketDetailsDTO> ticketDTOs = tickets == null ? null : tickets.stream().map(ticketDetailsDTOmapper).toList();

        return new UserTicketsDTO(userDTO, ticketDTOs);
        
    }
    
}
