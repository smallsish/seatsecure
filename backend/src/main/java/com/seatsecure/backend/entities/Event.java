package com.seatsecure.backend.entities;

import java.util.Date;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;

@Getter
@Setter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
//@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property="@view") // don't remove, in case need
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@Size(max = 20, message = "Event name should not be longer than 20 characters!")
    @NotNull(message = "Event name should not be empty!")
    //@Pattern(regexp = "^[0-9A-Za-z ]+$", message = "Only alphabets, numbers and spaces are allowed!")
    private String name;

    @NonNull
    private Date startDate;

    @NonNull
    private Date endDate;
    
    @ManyToOne
    @JoinColumn(name = "venue_id")
    private Venue venue;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Category> cats;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Run> runs;

}