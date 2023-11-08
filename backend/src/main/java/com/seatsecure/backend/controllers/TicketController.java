package com.seatsecure.backend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.seatsecure.backend.entities.User;
import com.seatsecure.backend.entities.DTO_mappers.complex.UserTicketsDTOmapper;
import com.seatsecure.backend.entities.DTOs.complex.UserTicketsDTO;
import com.seatsecure.backend.security.auth.AuthenticationService;
import com.seatsecure.backend.services.TicketMutatorService;
import com.seatsecure.backend.services.UserService;

@RequestMapping("/api/v1")
@RestController
//@PreAuthorize("hasRole('USER')")
public class TicketController {
    private UserService userService;
    private TicketMutatorService ticketService;
    private AuthenticationService authService;
    private UserTicketsDTOmapper userTicketsDTOmapper;

    public TicketController(UserService us, TicketMutatorService tms, AuthenticationService as, UserTicketsDTOmapper utDTOmapper) {
        userService = us;
        ticketService = tms;
        authService = as;
        userTicketsDTOmapper = utDTOmapper;
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


    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/tickets/{ticketId}")
    public UserTicketsDTO assignTicketToCurrentUser(@PathVariable("ticketId") Long ticketId){
        UserDetails ud = authService.getCurrentUserDetails(); // Get details of current user
        User user = userService.getUserByUsername(ud.getUsername());
        ticketService.assignTicketToUser(user.getId(), ticketId);

        return userTicketsDTOmapper.apply(user);
    }

}