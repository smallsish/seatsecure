package com.seatsecure.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.seatsecure.backend.entities.Event;
import com.seatsecure.backend.entities.Venue;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event,Long> {
    Optional<Event> findById(Long id);
    List<Event> findByVenue(Venue venue);
}
