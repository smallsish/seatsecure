package com.seatsecure.backend.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TicketUserQueue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long queueNumber;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cat_id")
    private Category cat;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "run_id")
    private Run run;

    @OneToMany(mappedBy = "tuQueue", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QueueEntry> entries;

}
