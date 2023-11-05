package com.seatsecure.backend.services;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import com.seatsecure.backend.entities.Event;
import com.seatsecure.backend.entities.Run;
import com.seatsecure.backend.repositories.RunRepository;

@Service
public class RunServiceImpl implements RunService {
    private RunRepository runRepo;
    private EventService eventService;

    public RunServiceImpl(RunRepository rr, EventService es) {
        runRepo = rr;
        eventService = es;
    }

    @Override
    public Run getRunById(Long id) {
        Optional<Run> run = runRepo.findById(id);
        if (run.isEmpty())
            return null;

        return run.get();
    }

    @Override
    public Event getEventOfRun(Long runId) {
        // Check if run exists
        Run run = getRunById(runId);
        if (run == null)
            return null;

        // Check if run's event exists
        Event e = run.getEvent();
        if (e == null)
            return null;

        return e;
    }

    @Override
    public List<Run> getRunsOfEvent(Long eventId) {
        // Check if event exists
        Event e = eventService.getEventById(eventId);
        if (e == null)
            return null;

        // Get runs of event
        return runRepo.getRunsByEvent(e);
    }

    @Override
    public Event addNewRunToEvent(Long eventId, Run run) {
        // Check if event exists
        Event e = eventService.getEventById(eventId);
        if (e == null || run == null)
            return null;

        // Add new run to database
        run.setEvent(e);
        runRepo.save(run);

        return e;
    }

    @Override
    public Run updateRun(Long id, Run newRunInfo) {
        // Check if run exists
        Run run = getRunById(id);
        if (run == null) return null;

        // Update run
        run.setName(newRunInfo.getName());
        run.setDescription(newRunInfo.getDescription());
        run.setStartDate(newRunInfo.getStartDate());
        run.setEndDate(newRunInfo.getEndDate());
        runRepo.save(run);

        return run;
    }

    @Override
    public Run deleteRunById(Long runId) {
        // Check if run exists
        Run run = getRunById(runId);
        if (run == null)
            return null;

        // Delete run from database
        runRepo.deleteById(runId);

        return run;
    }

}
