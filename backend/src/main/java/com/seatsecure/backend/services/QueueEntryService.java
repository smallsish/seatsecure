package com.seatsecure.backend.services;

import java.util.List;

import com.seatsecure.backend.entities.Category;
import com.seatsecure.backend.entities.QueueEntry;
import com.seatsecure.backend.entities.Run;
import com.seatsecure.backend.entities.TicketUserQueue;
import com.seatsecure.backend.entities.User;

public interface QueueEntryService {
    List<QueueEntry> listAllQueueEntriesPerQueue(TicketUserQueue queue);
    Long addEntryToQueue(User user, int numOfSeats, TicketUserQueue queue);
    QueueEntry deleteQueueEntry(long queueEntryID);
    QueueEntry getQueueEntry(long queueEntryID);
    QueueEntry updateEntry(long queueEntryID, QueueEntry newEntryInfo);
    Run getRunOfEntry(long entryID);
    Category getCatofEntry(long entryid);
}
