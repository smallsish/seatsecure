package com.seatsecure.backend.entities.DTOs.complex;

import com.seatsecure.backend.entities.DTOs.simple.UserDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsDTO {
    // User MUST be authenticated to receive this information!
    private UserDTO user;
    private String firstName;
    private String lastName;
    private String gender;
    private String password;
    private String email;
    private Integer phoneNumber;
}
