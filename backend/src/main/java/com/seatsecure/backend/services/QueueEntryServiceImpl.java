package com.seatsecure.backend.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.seatsecure.backend.entities.Category;
import com.seatsecure.backend.entities.QueueEntry;
import com.seatsecure.backend.entities.Run;
import com.seatsecure.backend.entities.TicketUserQueue;
import com.seatsecure.backend.entities.User;
import com.seatsecure.backend.entities.enums.Status;
import com.seatsecure.backend.repositories.QueueEntryRepository;

@Service
public class QueueEntryServiceImpl implements QueueEntryService {
    private QueueEntryRepository queueEntriesRepo;
    private TicketQueueService tqs;
    private RunService rs;
    private CatService cs;
    private UserService us;
    // private CategoryServiveImpl catSer;

    public QueueEntryServiceImpl(QueueEntryRepository queueEntriesRepo, TicketQueueService tqs, RunService rs, CatService cs, UserService us){
        this.queueEntriesRepo = queueEntriesRepo;
        this.tqs = tqs;
        this.rs = rs;
        this.cs = cs;
        this.us = us;
    }

    @Override
    public List<QueueEntry> listAllQueueEntriesPerQueue(TicketUserQueue queue){
        Long queueID = queue.getQueueNumber();
        return queueEntriesRepo.findByQueueEntryNumber(queueID);
    }


    @Override
    public Long addEntryToQueue(User user, int numOfSeats, TicketUserQueue queue){

        QueueEntry newQueueEntryInsert = new QueueEntry();
        newQueueEntryInsert.setNumOfSeats(numOfSeats);
        newQueueEntryInsert.setTuQueue(queue);
        newQueueEntryInsert.setUser(user);
        newQueueEntryInsert.setStatus(Status.BIDPLACED);
        return queueEntriesRepo.save(newQueueEntryInsert).getQueueEntryNumber();
    }

    @Override
    public QueueEntry updateEntry(long id, QueueEntry newEntryInfo) {
        // Check if queue exists
        QueueEntry entry = getQueueEntry(id);
        if (entry == null) return null;

        // Update entry
        entry.setNumOfSeats(newEntryInfo.getNumOfSeats());
        entry.setTuQueue(newEntryInfo.getTuQueue());
        entry.setUser(newEntryInfo.getUser());
        entry.setStatus(newEntryInfo.getStatus());
        queueEntriesRepo.save(entry);

        return entry;

    }

    @Override
    public QueueEntry getQueueEntry(long queueNumber){
        // Using Java Optional, as "findById" of Spring JPA returns an Optional object
        // Optional forces developers to explicitly handle the case of non-existent values
        // Here is an implementation using lambda expression to extract the value from Optional<Book>
        return queueEntriesRepo.findById(queueNumber).map(u -> {
            return u;
        }).orElse(null);
    }

    @Override
    public List<QueueEntry> getQueueEntriesOfUser(long userId) {
        // Check if user exists
        User u = us.getUserById(userId);

        return queueEntriesRepo.findByUser(u);
    }


    @Override
    public QueueEntry deleteQueueEntry(long queueNumber) {
        QueueEntry queueEntry = getQueueEntry(queueNumber);
        if (queueEntry == null) {
            return null;
        }
        queueEntriesRepo.deleteById(queueNumber);
        return queueEntry;
    }

    @Override
    public Run getRunOfEntry(long entryID){
        QueueEntry queueEntry = getQueueEntry(entryID);
        if (queueEntry == null) {
            return null;
        }
        long queueID = queueEntry.getTuQueue().getQueueNumber();
        TicketUserQueue queue = tqs.getQueue(queueID);
        if (queue == null){
            return null;
        }
        long runID = queue.getRun().getId();
        Run run = rs.getRunById(runID);
        if (run == null){
            return null;
        }
        return run;
    }

    @Override
    public Category getCatofEntry(long entryid){
        QueueEntry queueEntry = getQueueEntry(entryid);
        if (queueEntry == null) {
            return null;
        }
        long queueID = queueEntry.getTuQueue().getQueueNumber();
        TicketUserQueue queue = tqs.getQueue(queueID);
        if (queue == null){
            return null;
        }
        long catID = queue.getCat().getId();
        Category cat = cs.getCatById(catID);
        if (cat == null){
            return null;
        }
        return cat;
    }

}
