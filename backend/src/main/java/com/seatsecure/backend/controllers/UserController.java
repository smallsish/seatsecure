package com.seatsecure.backend.controllers;

import java.util.List;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.seatsecure.backend.entities.User;
import com.seatsecure.backend.entities.DTOs.UserDTO;
import com.seatsecure.backend.entities.DTOs.UserDetailsDTO;
import com.seatsecure.backend.entities.DTOs.mappers.UserDTOmapper;
import com.seatsecure.backend.exceptions.UserNotFoundException;
import com.seatsecure.backend.security.auth.AuthenticationService;
import com.seatsecure.backend.services.UserService;

@RequestMapping("/api/v1")
@RestController
@PreAuthorize("hasRole('ADMIN')")
public class UserController {
    private UserService userService;
    private AuthenticationService authService;
    private UserDTOmapper userDTOmapper;

    public UserController(UserService us, AuthenticationService as, UserDTOmapper userDTOmapper){
        this.userService = us;
        this.authService = as;
        this.userDTOmapper = userDTOmapper;
    }

    /**
     * List all users in the system
     * @return list of all users
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/users")
    @PreAuthorize("hasAuthority('admin:read')")
    public List<UserDTO> getUsers(){
        List<User> users = userService.listUsers();
        return users.stream().map(userDTOmapper).toList();
    }

    /**
     * Search for user with the given id
     * If there is no user with the given "id", throw a UserNotFoundException
     * @param id
     * @return User with the given id
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/users/{id}")
    public UserDetailsDTO getUserDetails(@PathVariable Long id){
        UserDetailsDTO user = userService.getUserDetailsDTO(id);
        if(user == null) throw new UserNotFoundException(id);
        
        return user;

    }

    /**
     * Update a user with new info
     * If there is no user with the given "id", throw a UserNotFoundException
     * @param id
     * @param newUserInfo
     * @return the updated user
     */
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable Long id, @Valid @RequestBody User newUserInfo){
        User user = userService.updateUser(id, newUserInfo);
        if(user == null) throw new UserNotFoundException(id);
        
        return user;
    }

    /**
     * Remove a user with the DELETE request to "/users/{id}"
     * If there is no user with the given "id", throw a UserNotFoundException
     * @param id
     */
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Long id){

        User user = userService.deleteUserById(id);
        if(user == null) throw new UserNotFoundException(id);

    }
}
