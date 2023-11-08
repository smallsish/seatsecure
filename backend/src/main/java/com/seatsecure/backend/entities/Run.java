package com.seatsecure.backend.entities;

import java.time.LocalDateTime;
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
public class Run {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Size(max = 100, message = "Name is too long!")
    private String name;

    @NonNull
    @Size(max = 1000, message = "Description is too long!")
    private String description;

    @NonNull
    private LocalDateTime startRunDate;

    @NonNull
    private LocalDateTime endRunDate;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @OneToMany(mappedBy = "run", cascade = CascadeType.ALL)
    private List<Ticket> tickets;

    @OneToMany(mappedBy = "run", cascade = CascadeType.ALL)
    private List<TicketUserQueue> tuQueue;

    @NonNull
    private LocalDateTime startBidDate;

    @NonNull
    private LocalDateTime endBidDate;

    @NonNull
    private Boolean algoRan;

    public Boolean isAlgoRan() {
        return algoRan;
    }
}
