package com.seatsecure.backend.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
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
import com.seatsecure.backend.entities.DTOs.EventDetailsDTO;
import com.seatsecure.backend.entities.DTOs.RunDTO;
import com.seatsecure.backend.entities.DTOs.mappers.EventDetailsDTOmapper;
import com.seatsecure.backend.entities.DTOs.mappers.RunDTOmapper;
import com.seatsecure.backend.exceptions.EventCreationError;
import com.seatsecure.backend.exceptions.EventNotFoundException;
import com.seatsecure.backend.exceptions.RunNotFoundException;
import com.seatsecure.backend.services.EventService;
import com.seatsecure.backend.services.RunService;

@RequestMapping("/api/v1")
@RestController
public class EventController {
    private EventService eventService;
    private RunService runService;
    private EventDetailsDTOmapper eventDetailsDTOmapper;
    private RunDTOmapper runDTOmapper;

    public EventController(EventService es, EventDetailsDTOmapper edDTOmapper, RunService rs, RunDTOmapper rDTOmapper) {
        eventService = es;
        runService = rs;
        eventDetailsDTOmapper = edDTOmapper;
        runDTOmapper = rDTOmapper;
    }

    /**
     * List all existing events
     * 
     * @return list of all events (all mapped to DTO)
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/events")
    public List<EventDetailsDTO> getEvents() {
        return eventService.getAll().stream().map(eventDetailsDTOmapper).collect(Collectors.toList());
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
    public EventDetailsDTO getEvent(@PathVariable Long id) {
        // Check if event exists
        Event event = eventService.getEventById(id);
        if (event == null)
            throw new EventNotFoundException(id);

        return eventDetailsDTOmapper.apply(event);
    }

    // @ResponseStatus(HttpStatus.OK)
    // @GetMapping("/events/{id}/runs")
    // public ResponseEntity<List<Object>> getRunsOfEvent(@PathVariable Long id){
    // // Check if event exists
    // Event e = eventService.getEventById(id);
    // if (e == null) throw new EventNotFoundException(id);

    // List<Run> runs = runService.getRunsOfEvent(id);
    // EventDetailsDTO edDTO = eventDetailsDTOmapper.apply(e);
    // List<Object> eventAndRuns = new ArrayList<>();
    // eventAndRuns.add(runs);
    // eventAndRuns.add(edDTO);

    // return ResponseEntity.of(eventAndRuns);
    // }

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
    public EventDetailsDTO addEvent(@Valid @RequestBody Event event) {
        Event e = eventService.addEvent(event);
        if (e == null)
            throw new EventCreationError();

        return eventDetailsDTOmapper.apply(e);
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
    public EventDetailsDTO updateEvent(@PathVariable Long id, @Valid @RequestBody Event newEventInfo) {
        Event event = eventService.updateEvent(id, newEventInfo);
        if (event == null)
            throw new EventNotFoundException(id);

        return eventDetailsDTOmapper.apply(event);
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
    public void deleteEvent(@PathVariable Long id) {

        Event event = eventService.deleteEventById(id);
        if (event == null)
            throw new EventNotFoundException(id);

        // return eventDetailsDTOmapper.apply(event);
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
    public EventDetailsDTO updateVenueOfEvent(@PathVariable("eventId") Long eventId,
            @PathVariable("venueId") Long venueId) {
        Event e = eventService.setVenueForEvent(eventId, venueId);
        if (e == null)
            throw new EventNotFoundException(eventId);

        return eventDetailsDTOmapper.apply(e);
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
    public EventDetailsDTO addRunToEvent(@PathVariable Long id, @Valid @RequestBody Run runInfo) {
        Event e = runService.addNewRunToEvent(id, runInfo);
        if (e == null)
            throw new EventNotFoundException(id);

        return eventDetailsDTOmapper.apply(e);
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
     * Delete an existing run
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

}
