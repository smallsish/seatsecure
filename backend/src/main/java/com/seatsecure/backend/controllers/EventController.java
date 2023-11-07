package com.seatsecure.backend.controllers;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.seatsecure.backend.entities.Event;
import com.seatsecure.backend.entities.Run;
import com.seatsecure.backend.entities.DTO_mappers.complex.EventRunsDTOmapper;
import com.seatsecure.backend.entities.DTO_mappers.complex.EventVenueDTOmapper;
import com.seatsecure.backend.entities.DTO_mappers.complex.RunTicketsDTOmapper;
import com.seatsecure.backend.entities.DTO_mappers.simple.EventDTOmapper;
import com.seatsecure.backend.entities.DTO_mappers.simple.RunDTOmapper;
import com.seatsecure.backend.entities.DTOs.complex.EventVenueDTO;
import com.seatsecure.backend.entities.DTOs.complex.RunTicketsDTO;
import com.seatsecure.backend.entities.DTOs.simple.EventDTO;
import com.seatsecure.backend.entities.DTOs.simple.RunDTO;
import com.seatsecure.backend.exceptions.event.EventCreationException;
import com.seatsecure.backend.exceptions.event.EventNotFoundException;
import com.seatsecure.backend.exceptions.run.RunNotFoundException;
import com.seatsecure.backend.services.EventService;
import com.seatsecure.backend.services.RunService;

@RequestMapping("/api/v1")
@RestController
public class EventController {
    private EventService eventService;
    private RunService runService;
    private EventVenueDTOmapper eventVenueDTOmapper;
    private RunDTOmapper runDTOmapper;
    private RunTicketsDTOmapper runTicketsDTOmapper;
    private EventDTOmapper eventDTOmapper;
    private EventRunsDTOmapper eventRunsDTOmapper;

    public EventController(EventService es, EventVenueDTOmapper evDTOmapper,
    RunService rs, RunDTOmapper rDTOmapper, RunTicketsDTOmapper rtDTOmapper, EventDTOmapper eDTOmapper,
    EventRunsDTOmapper erDTOmapper) {
        eventService = es;
        runService = rs;
        eventVenueDTOmapper = evDTOmapper;
        runDTOmapper = rDTOmapper;
        runTicketsDTOmapper = rtDTOmapper;
        eventDTOmapper = eDTOmapper;
        eventRunsDTOmapper = erDTOmapper;
    }

    /**
     * List all existing events and their venues
     * 
     * @return list of all events and their venues (mapped to DTO)
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/events")
    public List<EventVenueDTO> getEventVenues() {
        return eventService.getAll().stream().map(eventVenueDTOmapper).collect(Collectors.toList());
    }

    /**
     * Search for event with the given id
     * If there is no event with the given "id", throw a EventNotFoundException
     * 
     * @param id
     * @return Event obtained (mapped to DTO)
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/events/{id}")
    public EventVenueDTO getEventVenue(@PathVariable Long id) {
        // Check if event exists
        Event event = eventService.getEventById(id);
        if (event == null)
            throw new EventNotFoundException(id);

        return eventVenueDTOmapper.apply(event);
    }

    /**
     * Add a new event with POST request to "/events"
     * Runs and cats must be added separately
     * If there is an error with event creation, throw a EventCreationError
     * 
     * @param event
     * @return The new event that was added (mapped to DTO)
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/events")
    @PreAuthorize("hasAuthority('admin:create')")
    public EventDTO addEvent(@Valid @RequestBody Event event) {
        Event e = eventService.addEvent(event);
        if (e == null)
            throw new EventCreationException();

        return eventDTOmapper.apply(e);
    }

    /**
     * Update an event with new info
     * Cannot update id, cats, runs and venue
     * 
     * @param id
     * @param newEventInfo
     * @return the updated event
     */
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/events/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    public EventDTO updateEventBasic(@PathVariable Long id, @Valid @RequestBody Event newEventInfo) {
        Event event = eventService.updateEvent(id, newEventInfo);
        if (event == null)
            throw new EventNotFoundException(id);

        return eventDTOmapper.apply(event);
    }

    /**
     * Remove a event with the DELETE request to "/event/{id}"
     * If there is no event with the given "id", throw an EventNotFoundException
     * 
     * @param id
     */
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/events/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public EventDTO deleteEvent(@PathVariable Long id) {

        Event event = eventService.deleteEventById(id);
        if (event == null)
            throw new EventNotFoundException(id);

        return eventDTOmapper.apply(event);
    }

    /**
     * Update an event with a new venue
     * 
     * @param eventId
     * @param venueId
     * @return the updated event
     */
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/events/{eventId}/venue/{venueId}")
    @PreAuthorize("hasAuthority('admin:update')")
    public EventVenueDTO updateVenueOfEvent(@PathVariable("eventId") Long eventId,
            @PathVariable("venueId") Long venueId) {
        Event e = eventService.setVenueForEvent(eventId, venueId);
        if (e == null)
            throw new EventNotFoundException(eventId);

        return eventVenueDTOmapper.apply(e);
    }

    /**
     * Add a new run to an event
     * 
     * @param eventId
     * @param runInfo
     * @return the newly added run (mapped to DTO)
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/events/{id}/runs")
    @PreAuthorize("hasAuthority('admin:create')")
    public RunDTO addRunToEvent(@PathVariable Long id, @Valid @RequestBody Run runInfo) {
        Run r = runService.addNewRunToEvent(id, runInfo);
        if (r == null)
            throw new EventNotFoundException(id);

        return runDTOmapper.apply(r);
    }

    /**
     * Get a list of runs of an event
     * 
     * @param eventId
     * @return the list of runs of an event (mapped to DTO)
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/events/{id}/runs")
    public List<RunDTO> getRunsOfEvent(@PathVariable Long id) {
        // Check if event exists
        Event e = eventService.getEventById(id);
        if (e == null)
            throw new EventNotFoundException(id);

        List<RunDTO> runs = runService.getRunsOfEvent(id).stream().map(runDTOmapper).toList();
        return runs;
    }

    /**
     * Update the details of an existing run
     * 
     * @param runId
     * @return updated run (mapped to DTO)
     */
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/runs/{id}")
    public RunDTO updateRun(@PathVariable Long id, @Valid @RequestBody Run runInfo) {
        // Check if run exists
        Run r = runService.updateRun(id, runInfo);
        if (r == null)
            throw new RunNotFoundException(id);

        return runDTOmapper.apply(r);
    }

    /**
     * Delete an existing run v
     * @param runId
     * @return the deleted run (mapped to DTO)
     */
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/runs/{id}")
    public RunDTO deleteRun(@PathVariable Long id) {
        // Check if run exists
        Run r = runService.deleteRunById(id);
        if (r == null)
            throw new RunNotFoundException(id);

        return runDTOmapper.apply(r);
    }

    /**
     * Delete an existing run v
     * @param runId
     * @return the deleted run (mapped to DTO)
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/runs/{id}/tickets")
    public RunTicketsDTO getTicketsOfRun(@PathVariable Long id) {
        // Check if run exists
        Run r = runService.deleteRunById(id);
        if (r == null) {
            throw new RunNotFoundException(id);
        }
            
        return runTicketsDTOmapper.apply(r);
    }

}
