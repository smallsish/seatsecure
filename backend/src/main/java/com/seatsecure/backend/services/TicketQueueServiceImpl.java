package com.seatsecure.backend.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.seatsecure.backend.entities.Category;
import com.seatsecure.backend.entities.QueueEntry;
import com.seatsecure.backend.entities.Run;
import com.seatsecure.backend.entities.TicketUserQueue;
import com.seatsecure.backend.repositories.TicketQueueRepository;

@Service
public class TicketQueueServiceImpl implements TicketQueueService {
    private TicketQueueRepository queueRepo;
    private RunService runSer;
    private CatService catSer;

    public TicketQueueServiceImpl(TicketQueueRepository queueRepo, RunService runSer, CatService catSer){
        this.queueRepo = queueRepo;
        this.runSer = runSer;
        this.catSer = catSer;
    }

    @Override
    public List<TicketUserQueue> listAllQueues(){
        return queueRepo.findAll();
    }


    @Override
    public TicketUserQueue addQueuePerRunPerCat(Category category, Run run){

        TicketUserQueue newQueueInsert = TicketUserQueue.builder().cat(null).run(null)
        .entries(new ArrayList<QueueEntry>()).build();
        newQueueInsert = queueRepo.save(newQueueInsert);
        newQueueInsert.setCat(category);
        newQueueInsert.setRun(run);
        newQueueInsert.setEntries(new ArrayList<QueueEntry>());
        return queueRepo.save(newQueueInsert);
    }

    @Override
    public TicketUserQueue getQueuePerRunPerCat(Long catId, Long runId){
        //Run run = runSer.getRunById(runId);
        //Category cat = catSer.getCatById(catId);

        List<TicketUserQueue> queues = runSer.getTuQueueofRun(runId);
        for(TicketUserQueue queue : queues){
            if(queue.getCat().getId() == catId){
                return queue;
            }
        }
        return null;
    }

    @Override
    public Category getCatOfQueue(Long id) {
        TicketUserQueue queue = getQueue(id);
        return queue.getCat();
    }

    
    @Override
    public Run getRunOfQueue(Long id) {
        TicketUserQueue queue = getQueue(id);
        return queue.getRun();
    }

    @Override
    public TicketUserQueue getQueue(Long queueId){
        // Using Java Optional, as "findById" of Spring JPA returns an Optional object
        // Optional forces developers to explicitly handle the case of non-existent values
        // Here is an implementation using lambda expression to extract the value from Optional<Book>
        return queueRepo.findById(queueId).map(u -> {
            return u;
        }).orElse(null);
    }

    @Override
    public List<TicketUserQueue> listAllQueuesPerRun(Run run){
        return queueRepo.findByRun(run);
    }


    @Override
    public TicketUserQueue deleteQueue(Long queueId) {
        TicketUserQueue queueEntry = getQueue(queueId);
        if (queueEntry == null) {
            return null;
        }
        queueRepo.deleteById(queueId);
        return queueEntry;
    }

    @Override
    public TicketUserQueue updateQueuewithUpdatedEntries(List<QueueEntry> updatedEntrieslist, TicketUserQueue queue){
        TicketUserQueue queueEntry = getQueue(queue.getQueueNumber());
        if (queueEntry == null) {
            return null;
        }
        queueEntry.setEntries(updatedEntrieslist);
        queueRepo.save(queueEntry);
        return queueEntry;

    }

}