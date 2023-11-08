package com.seatsecure.backend.entities.DTO_mappers.bidEntry;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.seatsecure.backend.entities.QueueEntry;
import com.seatsecure.backend.entities.DTOs.bidEntry.BidDTO;

@Component
public class BidDTOmapper implements Function<QueueEntry, BidDTO>{

    @Override
    public BidDTO apply(QueueEntry entry) {
        return new BidDTO(entry.getQueueEntryNumber(), entry.getNumOfSeats(), entry.getStatus());
    }
    
}
