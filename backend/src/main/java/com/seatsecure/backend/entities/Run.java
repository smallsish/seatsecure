package com.seatsecure.backend.entities;

import java.sql.Time;
import java.util.Date;
import java.util.List;

import javax.print.attribute.standard.DateTimeAtCompleted;

import io.micrometer.common.lang.NonNull;
import jakarta.annotation.Generated;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Pattern;
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
    private Long runId;

    @NonNull
    @Size(max = 100, message = "Name is too long!")
    private String name;

    @NonNull
    @Size(max = 1000, message = "Description is too long!")
    private String description;

    @NonNull
    private Date startDate;

    @NonNull
    private Date endDate;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @OneToMany(mappedBy = "run", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Ticket> tickets;
}
