package com.seatsecure.backend.entities;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.seatsecure.backend.entities.enums.Gender;
import com.seatsecure.backend.entities.enums.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;


// @Setter
// @Getter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Role role; // uncomment this to use the ROLE entity
    //private String role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

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

    private Integer phoneNumber; // Check validity

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Ticket> ticketsPurchased;


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}