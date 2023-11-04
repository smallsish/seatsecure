package com.seatsecure.backend.services;

import java.util.List;

import com.seatsecure.backend.entities.User;

public interface UserService {
    // DTO methods
    //UserDetailsDTO getUserDetailsDTO(Long id);

    // Service methods
    List<User> listUsers();
    User getUserById(Long id);
    User getUserByUsername(String username);
    User addUser(User u);
    User updateUser(Long id, User newUserInfo);
    User deleteUserById(Long id);
}
