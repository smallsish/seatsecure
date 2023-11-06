package com.seatsecure.backend.services;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import com.seatsecure.backend.entities.Event;
import com.seatsecure.backend.entities.Run;
import com.seatsecure.backend.entities.TicketUserQueue;
import com.seatsecure.backend.exceptions.event.EventNotFoundException;
import com.seatsecure.backend.exceptions.event.NullEventException;
import com.seatsecure.backend.exceptions.run.RunNotFoundException;
import com.seatsecure.backend.exceptions.venue.VenueNotFoundException;
import com.seatsecure.backend.repositories.RunRepository;
import com.seatsecure.backend.repositories.TicketQueueRepository;

@Service
public class RunServiceImpl implements RunService {
    private RunRepository runRepo;
    private EventService eventService;
    private TicketQueueRepository ticketQueueRepo;

    public RunServiceImpl(RunRepository rr, EventService es, TicketQueueRepository tqRepo) {
        runRepo = rr;
        eventService = es;
        ticketQueueRepo = tqRepo;
    }

    /**
     * Get a Run by id
     * 
     * @param runId
     * @return The Run with the specified id
     * @throws RunNotFoundException If the Run with the specified id does not exist
     */
    @Override
    public Run getRunById(Long runId) {
        Optional<Run> run = runRepo.findById(runId);
        if (run.isEmpty()) {
            throw new RunNotFoundException(runId);
        }

        return run.get();
    }

    /**
     * Get the Event of a Run
     * 
     * @param runId
     * @return The Event of the Run with the specified id
     * @throws RunNotFoundException If the Run with the specified id does not exist
     * @throws NullEventException   If the Run is not associated with an Event
     */
    @Override
    public Event getEventOfRun(Long runId) {
        // Check if run exists
        Run run = getRunById(runId);

        // Check if run's event exists
        Event e = run.getEvent();
        if (e == null) {
            throw new NullEventException();
        }

        return e;
    }

    /**
     * Get all the Runs of an Event
     * 
     * @param eventId
     * @return A list of all the Runs od the Event with the specified id
     * @throws EventNotFoundException If the Event with the specified id does not
     *                                exist
     */
    @Override
    public List<Run> getRunsOfEvent(Long eventId) {
        // Check if event exists
        Event e = eventService.getEventById(eventId);

        // Get runs of event
        return runRepo.getRunsByEvent(e);
    }

    /**
     * Add a new Run to an Event
     * 
     * @param runId
     * @return The Event to which the new Run was added
     * @throws EventNotFoundException If the Event with the specified id does not
     *                                exist
     */
    @Override
    public Event addNewRunToEvent(Long eventId, Run runInfo) {
        // Check if event exists
        Event e = eventService.getEventById(eventId);

        // Add new run to database
        Run r = Run.builder().name(runInfo.getName()).description(runInfo.getDescription())
                .startDate(runInfo.getStartDate()).endDate(runInfo.getEndDate()).event(e).build();
        runRepo.save(r);

        return e;
    }

    /**
     * Update a Run with new info
     * 
     * @param runId
     * @param runInfo
     * @return The Run which was updated
     * @throws RunNotFoundException If the Run with the specified id does not exist
     */
    @Override
    public Run updateRun(Long runId, Run runInfo) {
        // Check if run exists
        Run run = getRunById(runId);

        // Update run
        run.setName(runInfo.getName());
        run.setDescription(runInfo.getDescription());
        run.setStartDate(runInfo.getStartDate());
        run.setEndDate(runInfo.getEndDate());
        runRepo.save(run);

        return run;
    }

    /**
     * Delete a Run by id
     * 
     * @param runId
     * @return The deleted Run
     * @throws RunNotFoundException If the Run with the specified id does not exist
     */
    @Override
    public Run deleteRunById(Long runId) {
        // Check if run exists
        Run run = getRunById(runId);

        // Delete run from database
        runRepo.deleteById(runId);

        return run;
    }

    /**
     * Get the TicketUserQueues associated with a Run
     * 
     * @param runId
     * @return A list of TicketUserQueues of the Run with the specified id
     * @throws RunNotFoundException If the Run with the specified id does not exist
     */
    @Override
    public List<TicketUserQueue> getQueueofRun(Long runId) {
        Run particularRun = getRunById(runId);
        
        return ticketQueueRepo.findByRun(particularRun);
    }

}
