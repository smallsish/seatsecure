package com.seatsecure.backend.services;

import java.util.List;

import com.seatsecure.backend.entities.User;

public interface UserService {
    List<User> listUsers();
    User getUserById(Long id);
    User addUser(User u);
    User updateUser(Long id, User newUserInfo);
    User deleteUserById(Long id);
}
