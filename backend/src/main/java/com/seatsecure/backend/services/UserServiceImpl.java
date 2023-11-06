package com.seatsecure.backend.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.seatsecure.backend.entities.QueueEntry;
import com.seatsecure.backend.entities.Ticket;
import com.seatsecure.backend.entities.User;
import com.seatsecure.backend.exceptions.user.UserNotFoundException;
import com.seatsecure.backend.repositories.UserRepository;


@Service
public class UserServiceImpl implements UserService {
   
    private UserRepository userRepo;
    

    public UserServiceImpl(UserRepository userRepo) {
        this.userRepo = userRepo;

    }


    /** 
     * Get all Users
     * 
     * @return A list of all Users
     */
    @Override
    public List<User> listUsers() {
        return userRepo.findAll();
    }

    /** 
     * Get the User with the specified id
     * 
     * @param userId
     * @return The User with the specified id
     * @throws UserNotFoundException If a User with the specified id does not exist
     */
    @Override
    public User getUserById(Long userId){
        Optional<User> user = userRepo.findById(userId);
        if (user.isEmpty()) {
            throw new UserNotFoundException(userId);
        }
        
        return user.get();
    }

    /** 
     * Get the User with the specified username
     * 
     * @param username
     * @return The User with the specified username
     * @throws UserNotFoundException If a User with the specified username does not exist
     */
    @Override
    public User getUserByUsername(String username){
        Optional<User> user = userRepo.findByUsername(username);
        if (user.isEmpty()) {
            throw new UserNotFoundException(username);
        }
        
        return user.get();
    }
    
    /** 
     * Add a new User
     * 
     * @param userInfo
     * @return The newly added User
     */
    @Override
    public User addUser(User userInfo) {
        // Create new user
        User u =  User.builder().role(userInfo.getRole()).username(userInfo.getUsername()).
        password(userInfo.getPassword()).firstName(userInfo.getFirstName()).lastName(userInfo.getLastName()).
        email(userInfo.getEmail()).gender(userInfo.getGender()).phoneNumber(userInfo.getPhoneNumber()).
        ticketsPurchased(new ArrayList<Ticket>()).queueEntries(new ArrayList<QueueEntry>()).build();

        return userRepo.save(u);
    }
    
    /** 
     * Update the info of the User with the specified id
     * (password, email, firstName, lastName, gender, phoneNumber, Username)
     * @param userId
     * @param userInfo
     * @return The updated User
     * @throws UserNotFoundException If a User with the specified id does not exist
     */
    @Override
    public User updateUser(Long userId, User userInfo){
        // Check if user exists
        User u = getUserById(userId);

        // Update user with new info
        u.setPassword(userInfo.getPassword());
        u.setEmail(userInfo.getEmail());
        u.setFirstName(userInfo.getFirstName());
        u.setLastName(userInfo.getLastName());
        u.setGender(userInfo.getGender());
        u.setPhoneNumber(userInfo.getPhoneNumber());
        u.setUsername(userInfo.getUsername());
        userRepo.save(u);
        
        return u;
    }

    /** 
     * Delete the User with the specified id
     *
     * @param userId
     * @return The deleted User
     * @throws UserNotFoundException If a User with the specified id does not exist
     */
    @Override
    public User deleteUserById(Long userId){
        // Check if User exists
        User user = getUserById(userId);

        // Delete the user
        userRepo.deleteById(userId);
        return user;
    }
}
