package com.seatsecure.backend.controllers;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.seatsecure.backend.entities.User;
import com.seatsecure.backend.entities.DTO_mappers.complex.UserDetailsDTOmapper;
import com.seatsecure.backend.entities.DTO_mappers.complex.UserTicketsDTOmapper;
import com.seatsecure.backend.entities.DTO_mappers.simple.UserDTOmapper;
import com.seatsecure.backend.entities.DTOs.complex.UserDetailsDTO;
import com.seatsecure.backend.entities.DTOs.complex.UserTicketsDTO;
import com.seatsecure.backend.entities.DTOs.simple.UserDTO;
import com.seatsecure.backend.exceptions.user.UnauthorizedUserException;
import com.seatsecure.backend.exceptions.user.UserNotFoundException;
import com.seatsecure.backend.security.auth.AuthenticationService;
import com.seatsecure.backend.services.TicketService;
import com.seatsecure.backend.services.UserService;

@RequestMapping("/api/v1")
@RestController
//@PreAuthorize("hasRole('USER')")
public class UserController {
    private UserService userService;
    private TicketService ticketService;
    private AuthenticationService authService;
    private UserDTOmapper userDTOmapper;
    private UserDetailsDTOmapper userDetailsDTOmapper;
    private UserTicketsDTOmapper userTicketsDTOmapper;
    // Will be injected via lazy setter injection

    /**
     * List all users in the system
     * @return list of all users (mapped to basic DTO)
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN') and hasAuthority('admin:read')")
    public List<UserDTO> getUsers(){
        List<User> users = userService.listUsers();
        return users.stream().map(userDTOmapper).toList();
    }

    /**
     * Search for user with the given id
     * If there is no user with the given "id", throw a UserNotFoundException
     * @param id
     * @return User with the given id, with confidential details (mapped to detailed DTO)
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/users/{id}")
    public UserDetailsDTO getUserDetails(@PathVariable Long id){
        User user = userService.getUserById(id);
        if(user == null) throw new UserNotFoundException(id);

        if (authService.isCurrentUser(user.getUsername())) {
            // Do mapping if authorized
            return userDetailsDTOmapper.apply(user);
        } else {
            throw new UnauthorizedUserException();
        }
    }

    /**
     * Update a user with new info
     * If there is no user with the given "id", throw a UserNotFoundException
     * @param id
     * @param newUserInfo
     * @return the updated user (mapped to detailed DTO)
     */
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/users/{id}")
    public UserDetailsDTO updateUser(@PathVariable Long id, @Valid @RequestBody User newUserInfo){
        User user = userService.getUserById(id);
        if(user == null) throw new UserNotFoundException(id);

        if (authService.isCurrentUser(user.getUsername())) {
            user = userService.updateUser(id, newUserInfo);
            // Do mapping if authorized
            return userDetailsDTOmapper.apply(user);
        } else {
            throw new UnauthorizedUserException();
        }
    }

    /**
     * Remove a user with the DELETE request to "/users/{id}"
     * If there is no user with the given "id", throw a UserNotFoundException
     * @param id
     * @return the deleted user (mapped to simple DTO)
     */
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/users/{id}")
    public UserDTO deleteUser(@PathVariable Long id){
        User user = userService.getUserById(id);
        if(user == null) throw new UserNotFoundException(id);

        if (authService.isCurrentUser(user.getUsername())) {
            user = userService.deleteUserById(id);
            return userDTOmapper.apply(user);
        } else {
            throw new UnauthorizedUserException();
        }
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
    public void injectUDTOmapper(UserDTOmapper uDTOmapper) {
        userDTOmapper = uDTOmapper;
    }

    @Lazy
    @Autowired
    public void injectUdDTOmapper(UserDetailsDTOmapper udDTOmapper) {
        userDetailsDTOmapper = udDTOmapper;
    }
    
    @Lazy
    @Autowired
    public void injectUtDTOmapper(UserTicketsDTOmapper utDTOmapper) {
        userTicketsDTOmapper = utDTOmapper;
    }

}
