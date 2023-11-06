package com.seatsecure.backend.entities;

import java.util.List;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Category {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Size(max = 100, message = "Name is too long!")
    private String name;

    @Size(max = 1000, message = "Description is too long!")
    @NonNull
    private String description;

    @NonNull
    private double price;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @OneToMany(mappedBy = "cat")
    private List<Seat> seats;
    
    @OneToMany(mappedBy = "cat", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Seat> queue;

    @OneToMany(mappedBy = "cat", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<TicketUserQueue> tuQueue;

    private int priority;
}
