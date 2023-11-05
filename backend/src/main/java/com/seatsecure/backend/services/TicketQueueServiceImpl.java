package com.seatsecure.backend.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.seatsecure.backend.entities.Category;
import com.seatsecure.backend.entities.Event;
import com.seatsecure.backend.entities.QueueEntry;
import com.seatsecure.backend.entities.Run;
import com.seatsecure.backend.entities.Ticket;
import com.seatsecure.backend.entities.TicketUserQueue;
import com.seatsecure.backend.entities.User;
import com.seatsecure.backend.exceptions.UserNotFoundException;
import com.seatsecure.backend.repositories.TicketQueueRepository;
import com.seatsecure.backend.repositories.UserRepository;

import jakarta.validation.OverridesAttribute;

@Service
public class TicketQueueServiceImpl implements TicketQueueService {
    private TicketQueueRepository queueRepo;
    private UserServiceImpl userSer;
    private TicketServiceImpl ticketSer;
    private RunServiceImpl runSer;
    // private CategoryServiveImpl catSer;

    public TicketQueueServiceImpl(TicketQueueRepository queueRepo, UserServiceImpl userSer, TicketServiceImpl ticketSer, RunServiceImpl runSer){
        this.queueRepo = queueRepo;
        this.userSer = userSer;
        this.ticketSer = ticketSer;
        this.runSer = runSer;
    }

    @Override
    public List<TicketUserQueue> listAllQueues(){
        return queueRepo.findAll();
    }


    @Override
    public Long addQueuePerRunPerCat(Category category, Run run){
        // Long UserId = user.getId();
        // if (!userSer.validateUser(UserId)){         
        //     throw new UserNotFoundException(UserId);
        // }
        // Long eventId = event.getId();
        // if (!catSer.validateUser(cat)){
        //     throw new CategoryNotFoundException(cat);
        // }
        
        TicketUserQueue newQueueInsert = TicketUserQueue.builder().category(category).run(run).build();
        return newQueueInsert.getQueueNumber();
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

    @Override
    public List<Ticket> bidSelection(int numOfSeats, Category cat, Long queueId){
        List<Ticket> finalTickets = new ArrayList<>();
        


        return finalTickets;
    }
}