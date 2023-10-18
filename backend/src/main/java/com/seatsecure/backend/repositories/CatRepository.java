package com.seatsecure.backend.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.seatsecure.backend.entities.Category;
import com.seatsecure.backend.entities.Event;

@Repository
public interface CatRepository extends JpaRepository<Category,Long> {
    Optional<Category> findById(Long id);
    List<Category> getCategoriesByEvent(Event e);
}
