package com.seatsecure.backend.security.auth;

import com.seatsecure.backend.entities.enums.Gender;
import com.seatsecure.backend.entities.enums.Role;

import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    
    @Size(max = 20, message = "Username should not be longer than 20 characters!")
    @NotNull(message = "Username should not be empty!")
    @Pattern(regexp = "^[0-9A-Za-z_]+$", message = "Only alphabets, numbers and underscores are allowed!")
    private String username;

    @NotNull(message = "Username should not be empty!")
    private String password;

    @Size(max = 20, message = "First name should not be longer than 20 characters!")
    @Pattern(regexp = "^[A-Za-z]+$", message = "Only alphabets are allowed!")
    private String firstName;

    @Size(max = 20, message = "Last name should not be longer than 20 characters!")
    @Pattern(regexp = "^[A-Za-z]+$", message = "Only alphabets are allowed!")
    private String lastName;

    @Email(message = "Invalid email address!")
    private String email;

    @Enumerated
    private Gender gender;

    // private String firstName;
    // private String lastName;
    // private String username;
    // private String email;
    // private String password;
    // private Gender gender;
    
    private Integer phoneNumber; 
    private Role role;
    
}
