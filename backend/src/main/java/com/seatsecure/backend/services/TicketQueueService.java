package com.seatsecure.backend.services;

import java.util.List;

import com.seatsecure.backend.entities.Category;
import com.seatsecure.backend.entities.TicketUserQueue;
import com.seatsecure.backend.entities.User;

public interface TicketQueueService {
    List<TicketUserQueue> listQueueNumbers();
    void addUserToQueue(User user, Category category, int numOfSeats);
    TicketUserQueue deleteQueueNumber(long queueNumber);

}
