<<<<<<< HEAD
package com.seatsecure.backend.entities;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
//@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property="@view") // don't remove, in case need
public class Venue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 20, message = "Venue name should not be longer than 20 characters!")
    @NotNull(message = "Venue name should not be empty!")
    //@Pattern(regexp = "^[0-9A-Za-z ]+$", message = "Only alphabets, numbers and spaces are allowed!")
    private String name;

    @Size(max = 30, message = "Address should not be longer than 30 characters!")
    @NotNull(message = "Address should not be empty!")
    //@Pattern(regexp = "^[0-9A-Za-z-#, ]+$", message = "Invalid characters used for address!")
    private String address;

    @OneToMany(mappedBy = "venue", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Event> events;

    @OneToMany(mappedBy = "venue", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Seat> seats;


=======
package com.seatsecure.backend.entities;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

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

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
//@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property="@view") // don't remove, in case need
public class Venue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 20, message = "Venue name should not be longer than 20 characters!")
    @NotNull(message = "Venue name should not be empty!")
    //@Pattern(regexp = "^[0-9A-Za-z ]+$", message = "Only alphabets, numbers and spaces are allowed!")
    private String name;

    @Size(max = 30, message = "Address should not be longer than 30 characters!")
    @NotNull(message = "Address should not be empty!")
    //@Pattern(regexp = "^[0-9A-Za-z-#, ]+$", message = "Invalid characters used for address!")
    private String address;

    
    @OneToMany(mappedBy = "venue", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Event> eventsList;


>>>>>>> 6421d053512ad97b0385a89b988bceb10ff095e8
}