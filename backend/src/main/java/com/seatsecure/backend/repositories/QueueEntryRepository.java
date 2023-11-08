package com.seatsecure.backend.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.seatsecure.backend.entities.QueueEntry;
import com.seatsecure.backend.entities.User;

@Repository
public interface QueueEntryRepository extends JpaRepository<QueueEntry,Long> {
    Optional<QueueEntry> findById(Long id);
    List<QueueEntry> findByQueueEntryNumber(long queueEntryNumber);
    List<QueueEntry> findByUser(User user);
}
