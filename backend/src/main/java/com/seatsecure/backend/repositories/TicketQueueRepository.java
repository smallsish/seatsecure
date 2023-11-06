package com.seatsecure.backend.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.seatsecure.backend.entities.Run;
import com.seatsecure.backend.entities.TicketUserQueue;

@Repository
public interface TicketQueueRepository extends JpaRepository<TicketUserQueue,Long> {
    Optional<TicketUserQueue> findById(Long id);
    List<TicketUserQueue> findByRun(Run run);
}