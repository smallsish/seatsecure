package com.seatsecure.backend.services;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import com.seatsecure.backend.entities.Category;
import com.seatsecure.backend.entities.Event;
import com.seatsecure.backend.exceptions.not_found.CatNotFoundException;
import com.seatsecure.backend.exceptions.not_found.EventNotFoundException;
import com.seatsecure.backend.exceptions.null_property.NullEventException;
import com.seatsecure.backend.repositories.CatRepository;

@Service
public class CatServiceImpl implements CatService {

    private EventService eventService;
    private CatRepository catRepo;

    public CatServiceImpl(EventService es, CatRepository cr) {
        eventService = es;
        catRepo = cr;
    }

    /**
     * Get the Category with the specified id
     * @param catId
     * @return Category with given id
     * @throws CatNotFoundException if there is no Category with the specified id
     */
    @Override
    public Category getCatById(Long catId) {
        Optional<Category> cat = catRepo.findById(catId);
        if (cat.isEmpty()) {
            throw new CatNotFoundException(catId);
        }

        return cat.get();
    }

    /**
     * Get the price of a Category
     * @param catId
     * @return Price of the Category with given id
     * @throws CatNotFoundException If there is no Category with the specified id
     */
    @Override
    public Double getPriceOfCat(Long catId) {
        // Try to get cat
        Category cat = getCatById(catId);

        return cat.getPrice();
    }

    /**
     * Get the Event of the Category with the specified id
     * @param catId
     * @return Event of the Category with the specified id
     * @throws CatNotFoundException if there is no Category with the specified id
     * @throws NullEventException if the Category does not have an Event it is associated with
     */
    @Override
    public Event getEventOfCat(Long catId) {
        Category c = getCatById(catId);

        // Check if cat's event exists
        Event e = c.getEvent();
        if (e == null) {
            throw new NullEventException();
        }

        return e;
    }

    /**
     * Get the Categories of the Event with the specified id
     * @param eventId
     * @return List of Categories of Event with the specified id
     * @throws EventNotFoundException If the Event cannot be found
     */
    @Override
    public List<Category> getCatsOfEvent(Long eventId) {
        // Check if event exists
        Event e = eventService.getEventById(eventId);

        // Get cats of event
        return catRepo.getCategoriesByEvent(e);
    }

    /**
     * Add a new Category to an Event with the specified id
     * @param eventId
     * @param catInfo
     * @return Newly added category
     * @throws EventNotFoundException If the Event cannot be found
     */
    @Override
    public Category addNewCatToEvent(Long eventId, Category catInfo) {
        // Check if event exists
        Event e = eventService.getEventById(eventId);

        // Set cat's event to e, save new cat to database
        Category c = Category.builder().name(catInfo.getName()).description(catInfo.getDescription()).price(catInfo.getPrice()).build();
        c.setEvent(e);
        catRepo.save(c);

        return c;
    }

    /**
     * Update a new Category with the specified id with new info
     * @param eventId
     * @param catInfo
     * @return Updated category
     * @throws CatNotFoundException If the Category cannot be found
     */
    @Override
    public Category updateCat(Long catId, Category catInfo){

        // Check if cat exists
        Category cat = getCatById(catId);

        // Update the properties of the existing cat
        cat.setName(catInfo.getName());
        cat.setDescription(catInfo.getDescription());
        cat.setPrice(catInfo.getPrice());
        catRepo.save(cat);

        return cat;
    }

    /**
     * Delete the Category with the specified id
     * @param catId
     * @return Deleted category
     * @throws CatNotFoundException If the Category cannot be found
     */
    @Override
    public Category deleteCatById(Long catId) {
        // Check if cat exists
        Category c = getCatById(catId);

        // Delete cat from database
        catRepo.deleteById(catId);

        return c;
    }

}
