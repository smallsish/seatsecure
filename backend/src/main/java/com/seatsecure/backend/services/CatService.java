package com.seatsecure.backend.services;

import java.util.List;

import com.seatsecure.backend.entities.Category;
import com.seatsecure.backend.entities.Event;

public interface CatService {

    Category getCatById(Long id);

    Event getEventOfCat(Long catId);

    List<Category> getCatsOfEvent(Long eventId);

    Category addNewCatToEvent(Long eventId, Category cat);

    Category updateCat(Long id, Category newCatInfo);

    Category deleteCatById(Long id);

}
