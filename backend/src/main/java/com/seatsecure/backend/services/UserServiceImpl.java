package com.seatsecure.backend.services;

import java.util.List;
import org.springframework.stereotype.Service;

import com.seatsecure.backend.entities.User;
import com.seatsecure.backend.repositories.UserRepository;


@Service
public class UserServiceImpl implements UserService {
   
    private UserRepository userRepo;
    

    public UserServiceImpl(UserRepository userRepo){
        this.userRepo = userRepo;
    }

    @Override
    public List<User> listUsers() {
        return userRepo.findAll();
    }

    
    @Override
    public User getUserById(Long userId){
        // Using Java Optional, as "findById" of Spring JPA returns an Optional object
        // Optional forces developers to explicitly handle the case of non-existent values
        // Here is an implementation using lambda expression to extract the value from Optional<Book>
        return userRepo.findById(userId).map(u -> {
            return u;
        }).orElse(null);
    }
    
    @Override
    public User addUser(User u) {
        return userRepo.save(u);
    }
    
    @Override
    public User updateUser(Long id, User newUserInfo){
        return userRepo.findById(id).map(u -> {u.setPassword(newUserInfo.getPassword());
            u.setEmail(newUserInfo.getEmail());
            u.setFirstName(newUserInfo.getFirstName());
            u.setLastName(newUserInfo.getLastName());
            u.setGender(newUserInfo.getGender());                   // different fields that user might want to change
            u.setPhoneNumber(newUserInfo.getPhoneNumber());
            u.setUsername(newUserInfo.getUsername());
            return userRepo.save(u);
        } ).orElse(null);
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

    @Override
    public Boolean validateUser(Long userId){
        try{
            getUserById(userId);
        }   catch(NullPointerException n){
            return false;
        }
        return true;
    }
}
