package com.seatsecure.backend.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.seatsecure.backend.entities.Category;
import com.seatsecure.backend.entities.Event;
import com.seatsecure.backend.entities.Run;
import com.seatsecure.backend.entities.Ticket;
import com.seatsecure.backend.entities.TicketUserQueue;
import com.seatsecure.backend.entities.User;
import com.seatsecure.backend.exceptions.UserNotFoundException;
import com.seatsecure.backend.repositories.TicketQueueRepository;
import com.seatsecure.backend.repositories.UserRepository;

@Service
public class TicketQueueServiceImpl implements TicketQueueService {
    private TicketQueueRepository queueRepo;
    private UserServiceImpl userSer;
    // private CategoryServiveImpl catSer;

    public TicketQueueServiceImpl(TicketQueueRepository queueRepo, UserServiceImpl userSer){
        this.queueRepo = queueRepo;
        this.userSer = userSer;
    }

    @Override
    public List<TicketUserQueue> listQueueNumbers(){
        return queueRepo.findAll();
    }

    @Override
    public List<TicketUserQueue> getList(Run run){
        return queueRepo.findAll();
    }

    @Override
    public Long addUserToQueue(User user, Category category, Run run, int numOfSeats){
        // Long UserId = user.getId();
        // if (!userSer.validateUser(UserId)){         
        //     throw new UserNotFoundException(UserId);
        // }
        // Long eventId = event.getId();
        // if (!catSer.validateUser(cat)){
        //     throw new CategoryNotFoundException(cat);
        // }
        
        TicketUserQueue newQueueInsert = TicketUserQueue.builder().category(category).user(user).run(run).numOfSeats(numOfSeats).build();
        return newQueueInsert.getQueueNumber();
    }

    @Override
    public TicketUserQueue getQueueEntry(Long queueNumber){
        // Using Java Optional, as "findById" of Spring JPA returns an Optional object
        // Optional forces developers to explicitly handle the case of non-existent values
        // Here is an implementation using lambda expression to extract the value from Optional<Book>
        return queueRepo.findById(queueNumber).map(u -> {
            return u;
        }).orElse(null);
    }


    @Override
    public TicketUserQueue deleteQueueNumber(Long queueNumber) {
        TicketUserQueue queueEntry = getQueueEntry(queueNumber);
        if (queueEntry == null) {
            return null;
        }
        queueRepo.deleteById(queueNumber);
        return queueEntry;
    }

    @Override
    public List<Ticket> bidSelection(int numOfSeats, Category cat, Long queueNumber){
        List<Ticket> finalTickets = new ArrayList<>();
        


        return finalTickets;
    }
}
