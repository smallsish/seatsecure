package com.seatsecure.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.seatsecure.backend.entities.User;
import com.seatsecure.backend.entities.DTOs.UserDetailsDTO;
import com.seatsecure.backend.entities.DTOs.mappers.UserDetailsDTOmapper;
import com.seatsecure.backend.exceptions.UnauthorizedUserException;
import com.seatsecure.backend.repositories.UserRepository;
import com.seatsecure.backend.security.auth.AuthenticationService;


@Service
public class UserServiceImpl implements UserService {
   
    private UserRepository userRepo;
    private AuthenticationService as;
    private UserDetailsDTOmapper userDetailsDTOmapper;
    

    public UserServiceImpl(UserRepository userRepo, AuthenticationService as, UserDetailsDTOmapper udDTOmapper){
        this.userRepo = userRepo;
        this.as = as;
        udDTOmapper = userDetailsDTOmapper;
    }

    // DTO methods
    public UserDetailsDTO getUserDetailsDTO(Long id) {
        // Check if requested user exists
        User requestedUser = getUserById(id);
        if (requestedUser == null) return null;

        // Check if current user is authenticated and is the right user
        if (as.isCurrentUser(requestedUser.getUsername())) {
            // Do mapping if authorized
            return userDetailsDTOmapper.apply(requestedUser);
        } else {
            throw new UnauthorizedUserException();
        }
        
    }


    // Service methods
    @Override
    public List<User> listUsers() {
        return userRepo.findAll();
    }

    
    @Override
    public User getUserById(Long userId){
        Optional<User> user = userRepo.findById(userId);
        if (user.isEmpty()) return null;
        
        return user.get();
    }

    @Override
    public User getUserByUsername(String username){
        Optional<User> user = userRepo.findByUsername(username);
        if (user.isEmpty()) return null;
        
        return user.get();
    }
    
    @Override
    public User addUser(User u) {
        return userRepo.save(u);
    }
    
    @Override
    public User updateUser(Long id, User newUserInfo){
        // Check if user exists
        User u = getUserById(id);
        if (u == null) return null;

        // Update user
        u.setPassword(newUserInfo.getPassword());
        u.setEmail(newUserInfo.getEmail());
        u.setFirstName(newUserInfo.getFirstName());
        u.setLastName(newUserInfo.getLastName());
        u.setGender(newUserInfo.getGender());
        u.setPhoneNumber(newUserInfo.getPhoneNumber());
        u.setUsername(newUserInfo.getUsername());
        userRepo.save(u);
        
        return u;
    }

    /**
     * Remove a user with the given id
     * Spring Data JPA does not return a value for delete operation
     */
    @Override
    public User deleteUserById(Long userId){
        User user = getUserById(userId);
        if (user == null) {
            return null;
        }
        userRepo.deleteById(userId);
        return user;
    }
}
