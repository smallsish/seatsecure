package com.seatsecure.backend.services;

import java.util.List;

import com.seatsecure.backend.entities.Category;
import com.seatsecure.backend.entities.Event;
import com.seatsecure.backend.entities.Run;
import com.seatsecure.backend.entities.Ticket;
import com.seatsecure.backend.entities.TicketUserQueue;
import com.seatsecure.backend.entities.User;

public interface TicketQueueService {
    List<TicketUserQueue> listQueueNumbers();
    Long addUserToQueue(User user, Category category, Run run, int numOfSeats);
    TicketUserQueue deleteQueueNumber(Long queueNumber);
    TicketUserQueue getQueueEntry(Long queueNumber);
    List<Ticket> bidSelection(int numOfSeats,Category cat);

}
