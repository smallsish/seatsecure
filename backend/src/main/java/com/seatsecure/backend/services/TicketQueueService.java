package com.seatsecure.backend.services;

import java.util.List;

import com.seatsecure.backend.entities.Category;
import com.seatsecure.backend.entities.Event;
import com.seatsecure.backend.entities.QueueEntry;
import com.seatsecure.backend.entities.Run;
import com.seatsecure.backend.entities.Ticket;
import com.seatsecure.backend.entities.TicketUserQueue;
import com.seatsecure.backend.entities.User;

public interface TicketQueueService {
    List<TicketUserQueue> listAllQueues();
    List<TicketUserQueue> listAllQueuesPerRun(Run run);
    Long addQueuePerRunPerCat(Category category, Run run);
    Long getQueuePerRunPerCat(Category category, Run run);
    TicketUserQueue deleteQueue(Long queueId);
    TicketUserQueue getQueue(Long queueId);
    TicketUserQueue updateQueuewithUpdatedEntries(List<QueueEntry> updatedEntrieslist, TicketUserQueue queue);

}
