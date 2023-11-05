package com.seatsecure.backend.entities.DTOs.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    // User MUST be authenticated to receive this information!
    private Long id;
    private String username;
}
