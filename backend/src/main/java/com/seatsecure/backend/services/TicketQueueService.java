package com.seatsecure.backend.services;

import java.util.List;

import com.seatsecure.backend.entities.Category;
import com.seatsecure.backend.entities.QueueEntry;
import com.seatsecure.backend.entities.Run;
import com.seatsecure.backend.entities.TicketUserQueue;

public interface TicketQueueService {
    List<TicketUserQueue> listAllQueues();
    List<TicketUserQueue> listAllQueuesPerRun(Run run);
    TicketUserQueue addQueuePerRunPerCat(Category category, Run run);
    TicketUserQueue getQueuePerRunPerCat(Long catId, Long runId);
    TicketUserQueue deleteQueue(Long queueId);
    TicketUserQueue getQueue(Long queueId);
    TicketUserQueue updateQueuewithUpdatedEntries(List<QueueEntry> updatedEntrieslist, TicketUserQueue queue);

}
