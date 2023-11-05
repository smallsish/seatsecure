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
import com.seatsecure.backend.repositories.QueueEntryRepository;
import com.seatsecure.backend.repositories.TicketQueueRepository;
import com.seatsecure.backend.repositories.UserRepository;

@Service
public class QueueEntryServiceImpl implements QueueEntryService {
    private QueueEntryRepository queueEntriesRepo;
    private UserServiceImpl userSer;
    private TicketServiceImpl ticketSer;
    private RunServiceImpl runSer;
    // private CategoryServiveImpl catSer;

    public QueueEntryServiceImpl(QueueEntryRepository queueEntriesRepo, UserServiceImpl userSer, TicketServiceImpl ticketSer, RunServiceImpl runSer){
        this.queueEntriesRepo = queueEntriesRepo;
        this.userSer = userSer;
        this.ticketSer = ticketSer;
        this.runSer = runSer;
    }

    @Override
    public List<QueueEntry> listAllQueueEntriesPerQueue(TicketUserQueue queue){
        Long queueID = queue.getQueueNumber();
        return queueEntriesRepo.findByQueue(queueID);
    }


    @Override
    public Long addEntryToQueue(User user, int numOfSeats, TicketUserQueue queue){
        // Long UserId = user.getId();
        // if (!userSer.validateUser(UserId)){         
        //     throw new UserNotFoundException(UserId);
        // }
        // Long eventId = event.getId();
        // if (!catSer.validateUser(cat)){
        //     throw new CategoryNotFoundException(cat);
        // }
        QueueEntry newQueueEntryInsert = new QueueEntry();
        newQueueEntryInsert.setNumOfSeats(numOfSeats);
        newQueueEntryInsert.setQueue(queue);
        newQueueEntryInsert.setUser(user);
        return queueEntriesRepo.save(newQueueEntryInsert).getQueueEntryNumber();
    }

    @Override
    public QueueEntry getQueueEntry(Long queueNumber){
        // Using Java Optional, as "findById" of Spring JPA returns an Optional object
        // Optional forces developers to explicitly handle the case of non-existent values
        // Here is an implementation using lambda expression to extract the value from Optional<Book>
        return queueEntriesRepo.findById(queueNumber).map(u -> {
            return u;
        }).orElse(null);
    }


    @Override
    public QueueEntry deleteQueueEntry(Long queueNumber) {
        QueueEntry queueEntry = getQueueEntry(queueNumber);
        if (queueEntry == null) {
            return null;
        }
        queueEntriesRepo.deleteById(queueNumber);
        return queueEntry;
    }

    @Override
    public List<Ticket> bidSelection(int numOfSeats, Category cat, Long queueNumber, ){
        List<Ticket> finalTickets = new ArrayList<>();
        


        return finalTickets;
    }
}
