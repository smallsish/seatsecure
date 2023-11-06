package com.seatsecure.backend.services;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import com.seatsecure.backend.entities.Category;
import com.seatsecure.backend.entities.Event;
import com.seatsecure.backend.repositories.CatRepository;

@Service
public class CatServiceImpl implements CatService {

    private EventService eventService;
    private CatRepository catRepo;

    public CatServiceImpl(EventService es, CatRepository cr) {
        eventService = es;
        catRepo = cr;
    }

    @Override
    public Category getCatById(Long id) {
        Optional<Category> cat = catRepo.findById(id);
        if (cat.isEmpty()) {
            return null;
        }

        return cat.get();
    }

    @Override
    public Event getEventOfCat(Long catId) {
        // Check if cat exists
        Category c = getCatById(catId);
        if (c == null)
            return null;

        // Check if cat's event exists
        Event e = c.getEvent();
        if (e == null)
            return null;

        return e;
    }

    @Override
    public List<Category> getCatsOfEvent(Long eventId) {
        // Check if event exists
        Event e = eventService.getEventById(eventId);
        if (e == null)
            return null;

        // Get cats of event
        return catRepo.getCategoriesByEvent(e);
    }

    @Override
    public Category addNewCatToEvent(Long eventId, Category cat) {
        // Check if event exists
        Event e = eventService.getEventById(eventId);
        if (e == null)
            return null;

        // Set cat's event to e, save new cat to database
        Category c = Category.builder().name(cat.getName()).description(cat.getDescription()).price(cat.getPrice()).build();
        c.setEvent(e);
        catRepo.save(c);

        return c;
    }

    @Override
    public Category updateCat(Long id, Category newCatInfo){

        // Check if cat exists
        Category cat = getCatById(id);
        if (cat == null) return null;

        cat.setName(newCatInfo.getName());
        cat.setDescription(newCatInfo.getDescription());
        cat.setPrice(newCatInfo.getPrice());
        catRepo.save(cat);

        return cat;
    }

    @Override
    public Category deleteCatById(Long id) {
        // Check if cat exists
        Category c = getCatById(id);
        if (c == null)
            return null;

        // Delete cat from database
        catRepo.deleteById(id);

        return c;
    }

}
