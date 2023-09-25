package com.seatsecure.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.seatsecure.backend.entities.Venue;


@Repository
public interface VenueRepository extends JpaRepository<Venue,Long> {
}
