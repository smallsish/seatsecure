package com.seatsecure.backend.security.auth;

import com.seatsecure.backend.entities.enums.Gender;
import com.seatsecure.backend.entities.enums.Role;

import io.micrometer.common.lang.NonNull;
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
    @Pattern(regexp = "^[A-Za-z_]+$", message = "Only alphabets and underscores are allowed!")
    @NonNull
    private String firstName;

    @Size(max = 20, message = "Last name should not be longer than 20 characters!")
    @Pattern(regexp = "^[A-Za-z_]+$", message = "Only alphabets and underscores are allowed!")
    @NonNull
    private String lastName;

    @Size(max = 50, message = "Email is too long!")
    @Email(message = "Invalid email address!")
    @NonNull
    private String email;

    @Enumerated
    @NonNull
    private Gender gender;

    @Pattern(regexp = "^[89]{1}[0-9]{7}$", message = "Isnvalid phone number!")
    @NonNull
    private String phoneNumber;

    private Role role;
    
}
