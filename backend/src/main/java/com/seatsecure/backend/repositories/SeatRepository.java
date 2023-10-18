package com.seatsecure.backend.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.seatsecure.backend.entities.Category;
import com.seatsecure.backend.entities.Seat;
import com.seatsecure.backend.entities.Venue;

@Repository
public interface SeatRepository extends JpaRepository<Seat,Long> {
    Optional<Seat> findById(Long id);


    List<Seat> getSeatsByVenue(Venue v);
    List<Seat> getSeatsByCat(Category c);
}
