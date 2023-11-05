package com.seatsecure.backend.services;

import java.util.List;

import com.seatsecure.backend.entities.Category;
import com.seatsecure.backend.entities.Event;
import com.seatsecure.backend.entities.QueueEntry;
import com.seatsecure.backend.entities.Run;
import com.seatsecure.backend.entities.Ticket;
import com.seatsecure.backend.entities.TicketUserQueue;
import com.seatsecure.backend.entities.User;

public interface QueueEntryService {
    List<QueueEntry> listAllQueueEntriesPerQueue(TicketUserQueue queue);
    Long addEntryToQueue(User user, int numOfSeats, TicketUserQueue queue);
    QueueEntry deleteQueueEntry(Long queueEntryID);
    QueueEntry getQueueEntry(Long queueEntryID);
    

}
