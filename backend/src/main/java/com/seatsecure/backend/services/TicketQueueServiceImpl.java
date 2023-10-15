package com.seatsecure.backend.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.seatsecure.backend.entities.Category;
import com.seatsecure.backend.entities.TicketUserQueue;
import com.seatsecure.backend.entities.User;
import com.seatsecure.backend.repositories.TicketQueueRepository;
import com.seatsecure.backend.repositories.UserRepository;

@Service
public class TicketQueueServiceImpl implements TicketQueueService {
    private TicketQueueRepository queueRepo;
    //private UserServiceImpl userSer;
    //private CategoryServiveImpl catSer;

    public TicketQueueServiceImpl(TicketQueueRepository queueRepo){
        this.queueRepo = queueRepo;
    }


    public List<TicketUserQueue> listQueueNumbers(){
        return queueRepo.findAll();
    }

    public void addUserToQueue(User user, Category category, int numOfSeats){
        user.se
    }
}
