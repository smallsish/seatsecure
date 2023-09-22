package com.seatsecure.backend.controllers;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.seatsecure.backend.entities.User;
import com.seatsecure.backend.exceptions.UserNotFoundException;
import com.seatsecure.backend.security.auth.AuthenticationResponse;
import com.seatsecure.backend.security.auth.AuthenticationService;
import com.seatsecure.backend.security.auth.RegisterRequest;
import com.seatsecure.backend.services.UserService;

@RequestMapping("/api/v1")
@RestController
public class UserController {
    private UserService userService;
    private AuthenticationService authService;

    public UserController(UserService us, AuthenticationService as){
        this.userService = us;
        this.authService = as;
    }

    /**
     * List all users in the system
     * @return list of all users
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/users")
    public List<User> getUsers(){
        return userService.listUsers();
    }

    /**
     * Search for user with the given id
     * If there is no user with the given "id", throw a UserNotFoundException
     * @param id
     * @return User with the given id
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/users/{id}")
    public User getUser(@Pattern(regexp = "[0-9]+") @PathVariable Long id){
        User user = userService.getUserById(id);

        if(user == null) throw new UserNotFoundException(id);
        return userService.getUserById(id);

    }
    /**
     * Add a new user with POST request to "/users"
     * @param user
     * @return The new user that was added
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/users")
    public ResponseEntity<AuthenticationResponse> addUser(
        @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(authService.register(request));
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
