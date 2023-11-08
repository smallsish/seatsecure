package com.seatsecure.backend.controllers;

import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.seatsecure.backend.entities.Ticket;
import com.seatsecure.backend.entities.User;
import com.seatsecure.backend.entities.DTO_mappers.complex.TicketDetailsDTOmapper;
import com.seatsecure.backend.entities.DTO_mappers.complex.UserTicketsDTOmapper;
import com.seatsecure.backend.entities.DTOs.complex.TicketDetailsDTO;
import com.seatsecure.backend.entities.DTOs.complex.UserTicketsDTO;
import com.seatsecure.backend.exceptions.others.UnauthorizedUserException;
import com.seatsecure.backend.security.auth.AuthenticationService;
import com.seatsecure.backend.services.TicketAccessorService;
import com.seatsecure.backend.services.TicketMutatorService;
import com.seatsecure.backend.services.UserService;

@RequestMapping("/api/v1")
@RestController
//@PreAuthorize("hasRole('USER')")
public class TicketController {
    private UserService userService;
    private TicketAccessorService ticketAService;
    private TicketMutatorService ticketMService;
    private AuthenticationService authService;
    private UserTicketsDTOmapper userTicketsDTOmapper;
    private TicketDetailsDTOmapper ticketDetailsDTOmapper;

    public TicketController(UserService us, TicketAccessorService tas, TicketMutatorService tms,
    AuthenticationService as, UserTicketsDTOmapper utDTOmapper, TicketDetailsDTOmapper tdDTOmapper) {
        userService = us;
        ticketAService = tas;
        ticketMService = tms;
        authService = as;
        userTicketsDTOmapper = utDTOmapper;
        ticketDetailsDTOmapper = tdDTOmapper;
    }

        /**
     * Remove a user with the DELETE request to "/users/{id}"
     * If there is no user with the given "id", throw a UserNotFoundException
     * @param id
     * @return the deleted user (mapped to simple DTO)
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/tickets")
    public UserTicketsDTO getTicketsOfCurrentUser(){
        UserDetails ud = authService.getCurrentUserDetails(); // Get details of current user
        User user = userService.getUserByUsername(ud.getUsername());  

        return userTicketsDTOmapper.apply(user);
    }

    // USER TO BUY TICKETS - PICK A CAT AND ADD TO RAFFLE


    // Must be authorized to do so
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/tickets/{id}")
    public TicketDetailsDTO getTicket(@PathVariable Long id){
        // Check if user owns the ticket
        UserDetails ud = authService.getCurrentUserDetails(); // Get details of current user
        User currentUser = userService.getUserByUsername(ud.getUsername());
        
        if (ticketAService.userOwnsTicket(currentUser.getId(), id)) {
            Ticket t = ticketAService.getTicketById(id);
            return ticketDetailsDTOmapper.apply(t);
        } else {
            throw new UnauthorizedUserException();
        }
    }

    
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/tickets/{id}")
    public TicketDetailsDTO assignTicketToCurrentUser(@PathVariable Long id){
        UserDetails ud = authService.getCurrentUserDetails(); // Get details of current user
        User user = userService.getUserByUsername(ud.getUsername());
        Ticket t = ticketMService.assignTicketToUser(user.getId(), id);

       return ticketDetailsDTOmapper.apply(t);
    }

    // @ResponseStatus(HttpStatus.OK)
    // @DeleteMapping("/tickets/{id}")
    // public TicketDetailsDTO unassignTicketFromCurrentUser(@PathVariable Long id){
    //     UserDetails ud = authService.getCurrentUserDetails(); // Get details of current user
    //     User user = userService.getUserByUsername(ud.getUsername());

    //     Ticket t = ticketMService.unassignTicketFromUser(id);

    //     return ticketDetailsDTOmapper.apply(t);
    // }

}