package com.seatsecure.backend.services;

import java.util.List;

import com.seatsecure.backend.entities.Category;
import com.seatsecure.backend.entities.Event;

public interface CatService {

    Category getCatById(Long id);

    Double getPriceOfCat(Long catId);

    Event getEventOfCat(Long catId);

    List<Category> getCatsOfEvent(Long eventId);

    Category addNewCatToEvent(Long eventId, Category cat);

    Category updateCat(Long catId, Category newCatInfo);

    Category deleteCatById(Long catId);

}
