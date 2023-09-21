package com.seatsecure.backend.entities;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Setter
@Getter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // private String username;

    // private String password;

    // private String firstName;

    // private String lastName;

    // private String email;

    // private String gender; // Should be implemented with dropdown boxes (fixed options)

    // private Integer phoneNumber;

     @Enumerated(EnumType.STRING)
    private Role role; // uncomment this to use the ROLE entity
    //private String role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        //return List.of(new SimpleGrantedAuthority(role.name())); // uncomment this to revert back to ROLE entity
        return role.getAuthorities();
    }

    @Size(max = 20, message = "Username should not be longer than 20 characters!")
    @Pattern(regexp = "^[0-9A-Za-z_]+$", message = "Only alphabets, numbers and underscores are allowed!")
    private String username;

    //@Size(max = 20, message = "Password should not be longer than 20 characters!") // there should not be size validation since the hash passsword is more than 20
    private String password;

    //@Size(max = 20, message = "First name should not be longer than 20 characters!")
    @Pattern(regexp = "^[A-Za-z]+$", message = "Only alphabets are allowed!")
    private String firstName;

    //@Size(max = 20, message = "Last name should not be longer than 20 characters!")
    @Pattern(regexp = "^[A-Za-z]+$", message = "Only alphabets are allowed!")
    private String lastName;

    @Email(message = "Invalid email address!")
    private String email;

    private String gender; // Should be implemented with dropdown boxes (fixed options)

    private Integer phoneNumber;

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return true;
    }

}
