package com.seatsecure.backend.entities.DTOs.mappers;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.seatsecure.backend.entities.User;
import com.seatsecure.backend.entities.DTOs.UserDTO;
import com.seatsecure.backend.entities.DTOs.UserDetailsDTO;
import com.seatsecure.backend.entities.enums.Gender;

@Component
public class UserDetailsDTOmapper implements Function<User, UserDetailsDTO> {

    @Autowired
    private UserDTOmapper userDTOmapper;

    @Override
    public UserDetailsDTO apply(User user) {
        UserDTO userDTO = userDTOmapper.apply(user);
        return new UserDetailsDTO(userDTO, user.getFirstName(), user.getLastName(), 
        user.getGender() == Gender.MALE ? "Male" : "Female", user.getPassword(), user.getEmail(), user.getPhoneNumber());
    }

}
