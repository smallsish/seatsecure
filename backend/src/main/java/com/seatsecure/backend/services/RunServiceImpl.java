package com.seatsecure.backend.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.seatsecure.backend.entities.Event;
import com.seatsecure.backend.entities.Run;
import com.seatsecure.backend.entities.Ticket;
import com.seatsecure.backend.entities.TicketUserQueue;
import com.seatsecure.backend.exceptions.not_found.EventNotFoundException;
import com.seatsecure.backend.exceptions.not_found.VenueNotFoundException;
import com.seatsecure.backend.exceptions.null_property.NullEventException;
import com.seatsecure.backend.exceptions.not_found.RunNotFoundException;
import com.seatsecure.backend.repositories.RunRepository;
import com.seatsecure.backend.repositories.TicketQueueRepository;
import com.seatsecure.backend.repositories.TicketRepository;

@Lazy
@Service
public class RunServiceImpl implements RunService {
    private RunRepository runRepo;
    private EventService eventService;
    private TicketRepository ticketRepo;
    private TicketQueueRepository tqRepo;

    public RunServiceImpl(RunRepository rr, EventService es, TicketRepository tr, TicketQueueRepository tqr) {
        runRepo = rr;
        eventService = es;
        ticketRepo = tr;
        tqRepo = tqr;
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
     * @return The Run that was newly added to the Event
     * @throws EventNotFoundException If the Event with the specified id does not
     *                                exist
     */
    @Override
    public Run addNewRunToEvent(Long eventId, Run runInfo) {
        // Check if event exists
        Event e = eventService.getEventById(eventId);

        // Add new run to database
        Run r = Run.builder().name(runInfo.getName()).description(runInfo.getDescription())
                .startDate(runInfo.getStartDate()).endDate(runInfo.getEndDate()).event(e).build();
        return runRepo.save(r);

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

        // Get all tickets with 'run' set to run
        // We need to set them to null first
        List<Ticket> runTickets = ticketRepo.findByRun(run);
        for (Ticket t : runTickets) {
            t.setRun(null);
            ticketRepo.save(t);
        }

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
    public List<TicketUserQueue> getTuQueueofRun(Long runId) {
        Run particularRun = getRunById(runId);
        
        return tqRepo.findByRun(particularRun);
    }


    // Validation methods
    /**
     * Check whether a Venue is free over a period of time
     * 
     * @param venueId
     * @param date
     * @return True if the Venue is available during that time, false otherwise
     * @throws VenueNotFoundException If a Venue with the specified id does not exist
    */
    @Override
    public Boolean dateValidAtVenue(Long venueId, Date startDate, Date endDate) {
        // Get event at venue
        Event e = eventService.getEventAtVenue(venueId);

        // If no event at the venue currently, date is valid
        if (e == null) return true;
        else {
            // Get all runs of the event at the venue
            List<Run> runs = getRunsOfEvent(e.getId());
            if (runs.size() == 0) return true; // If no runs at the event, return true

            List<Date> startDates = runs.stream().map(Run::getStartDate).toList();
            List<Date> endDates = runs.stream().map(Run::getEndDate).toList();

            // Check if any run intercepts with specified date
            for (int i = 0; i < runs.size(); i++) {
                Date sd = startDates.get(i);
                Date ed = endDates.get(i);

                if (endDate.compareTo(sd) == 1 || startDate.compareTo(ed) == -1) {
                    return false;
                }
            }

            return true;
        }
    }


    /**
     * Check whether the startDate of a run is before the endDate of a run
     * 
     * @param run
     * @return True if startDate < endDate. False otherwise
     */

    @Override
    public Boolean runDatesAreValid(Run run) {
        return run.getStartDate().compareTo(run.getEndDate()) == -1;
    }

    /*
     * SETTER INJECTORS
     */

    @Autowired
    public void injectRunRepo(RunRepository rr) {
        runRepo = rr;
    }

    @Autowired
    public void injectEventService(EventService es) {
        eventService = es;
    }

    @Autowired
    public void injectTqRepository(TicketQueueRepository tqr) {
        tqRepo = tqr;
    }

    @Autowired
    public void injectTicketRepository(TicketRepository tr) {
        ticketRepo = tr;
    }

}

