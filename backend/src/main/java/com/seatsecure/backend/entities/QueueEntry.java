package com.seatsecure.backend.entities;

import com.seatsecure.backend.entities.enums.Status;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "queueentry")
public class QueueEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long queueEntryNumber;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    private int numOfSeats;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ticketuserqueue_id")
    private TicketUserQueue tuQueue;

    @Enumerated
    private Status status;

}
