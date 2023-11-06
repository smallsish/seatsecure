package com.seatsecure.backend.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.seatsecure.backend.entities.QueueEntry;
import com.seatsecure.backend.entities.TicketUserQueue;
import com.seatsecure.backend.entities.User;
import com.seatsecure.backend.repositories.QueueEntryRepository;

@Service
public class QueueEntryServiceImpl implements QueueEntryService {
    private QueueEntryRepository queueEntriesRepo;
    // private CategoryServiveImpl catSer;

    public QueueEntryServiceImpl(QueueEntryRepository queueEntriesRepo){
        this.queueEntriesRepo = queueEntriesRepo;
    }

    @Override
    public List<QueueEntry> listAllQueueEntriesPerQueue(TicketUserQueue queue){
        Long queueID = queue.getQueueNumber();
        return queueEntriesRepo.findByQueueEntryNumber(queueID);
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
        newQueueEntryInsert.setTuQueue(queue);
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

}
