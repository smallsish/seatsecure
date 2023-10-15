package com.seatsecure.backend.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.seatsecure.backend.entities.Run;

@Repository
public interface RunRepository extends JpaRepository<Run,Long> {
}
