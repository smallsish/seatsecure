package com.seatsecure.backend.services;

import java.util.List;

import com.seatsecure.backend.entities.Category;
import com.seatsecure.backend.entities.Event;
import com.seatsecure.backend.entities.Venue;

public interface CatService {

    Category getCatById(Long id);

    Event getEventOfCat(Long catId);

    List<Category> getCatsOfEvent(Long eventId);

    Event addNewCatToEvent(Long eventId, Category cat);

    Category updateCat(Long id, Category newCatInfo);

    Category deleteCatById(Long id);

}