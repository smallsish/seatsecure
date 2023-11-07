package com.seatsecure.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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
import com.seatsecure.backend.services.TicketService;
import com.seatsecure.backend.services.UserService;

@RequestMapping("/api/v1")
@RestController
//@PreAuthorize("hasRole('USER')")
public class TicketController {
    private UserService userService;
    private TicketService ticketService;
    private AuthenticationService authService;
    private UserTicketsDTOmapper userTicketsDTOmapper;
    // Above dependencies will be injected via lazy setter injection

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
    public UserTicketsDTO assignTicketToCurrentUser(@PathVariable("userId") Long userId, @PathVariable("ticketId") Long ticketId){
        UserDetails ud = authService.getCurrentUserDetails(); // Get details of current user
        User user = userService.getUserByUsername(ud.getUsername());
        ticketService.assignTicketToUser(user.getId(), ticketId);

        return userTicketsDTOmapper.apply(user);
    }


    /*
     * SETTER INJECTORS
     */

    @Lazy
    @Autowired
    public void injectUserService(UserService us) {
        userService = us;
    }

    @Lazy
    @Autowired
    public void injectTicketService(TicketService ts) {
        ticketService = ts;
    }

    @Lazy
    @Autowired
    public void injectAuthService(AuthenticationService as) {
        authService = as;
    }
    
    @Lazy
    @Autowired
    public void injectUtDTOmapper(UserTicketsDTOmapper utDTOmapper) {
        userTicketsDTOmapper = utDTOmapper;
    }
}