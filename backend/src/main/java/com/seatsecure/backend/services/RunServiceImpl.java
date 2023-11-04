package com.seatsecure.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.seatsecure.backend.entities.Run;
import com.seatsecure.backend.entities.Ticket;
import com.seatsecure.backend.entities.TicketUserQueue;
import com.seatsecure.backend.repositories.RunRepository;


@Service
public class RunServiceImpl implements RunService {
   
    private RunRepository runRepo;

    public RunServiceImpl(RunRepository runRepo){
        this.runRepo = runRepo;
    }

    @Override
    public List<Run> listRuns() {
        return runRepo.findAll();
    }

    
    @Override
    public Run getRunById(Long runId){
        Optional<Run> e = runRepo.findById(runId);
        if (e.isEmpty()) return null;

        return e.get();
    }
    
    @Override
    public Run addRun(Run u) {
        return runRepo.save(u);
    }
    
    @Override
    public Run updateRun(Long id, Run newRunInfo){
        Optional<Run> run = runRepo.findById(id);
        if (run.isEmpty()) return null; // Venue not found

        Run e = run.get();
        e.setName(newRunInfo.getName());
        e.setStartDate(newRunInfo.getStartDate());
        e.setEndDate(newRunInfo.getEndDate());
        e.setDescription(newRunInfo.getDescription());
        e.setEvent(newRunInfo.getEvent());
        runRepo.save(e);

        return e;
    }

    @Override
    public Run deleteRunById(Long runId){
        Run run = getRunById(runId);
        if (run == null) return null;

        runRepo.deleteById(runId);
        return run;
    }

    @Override
    public List<TicketUserQueue> getQueues(Long runId) {
        Run run = getRunById(runId);
        if (run == null) return null;

        return run.getTicketUserQueue();
    }

    @Override
    public List<Ticket> getTickets(Long runId) {
        Run run = getRunById(runId);
        if (run == null) return null;

        return run.getTickets();

    }

}
