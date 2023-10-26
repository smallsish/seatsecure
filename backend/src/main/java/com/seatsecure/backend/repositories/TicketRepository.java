package com.seatsecure.backend.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.seatsecure.backend.entities.Run;
import com.seatsecure.backend.entities.Ticket;
import com.seatsecure.backend.entities.User;

import java.util.List;


@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Optional<Ticket> findById(Long id);
    List<Ticket> findByRun(Run run);
    List<Ticket> findByUser(User user);
}
