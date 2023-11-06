package com.seatsecure.backend.entities.DTO_mappers.user;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.seatsecure.backend.entities.User;
import com.seatsecure.backend.entities.DTOs.user.UserDTO;

@Component
public class UserDTOmapper implements Function<User, UserDTO> {

    @Override
    public UserDTO apply(User user) {
        return new UserDTO(user.getId(), user.getUsername());
    }

}
