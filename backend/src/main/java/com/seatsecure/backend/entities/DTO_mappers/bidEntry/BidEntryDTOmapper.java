package com.seatsecure.backend.entities.DTO_mappers.bidEntry;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.seatsecure.backend.entities.Category;
import com.seatsecure.backend.entities.QueueEntry;
import com.seatsecure.backend.entities.Run;
import com.seatsecure.backend.entities.DTO_mappers.simple.CatDTOmapper;
import com.seatsecure.backend.entities.DTO_mappers.simple.RunDTOmapper;
import com.seatsecure.backend.entities.DTOs.bidEntry.BidDTO;
import com.seatsecure.backend.entities.DTOs.bidEntry.BidEntryDetailsDTO;
import com.seatsecure.backend.entities.DTOs.simple.CatDTO;
import com.seatsecure.backend.entities.DTOs.simple.RunDTO;
import com.seatsecure.backend.services.QueueEntryService;


@Component
public class BidEntryDTOmapper implements Function<QueueEntry, BidEntryDetailsDTO>{

    @Lazy
    @Autowired
    private RunDTOmapper runDTOmapper;

    @Lazy
    @Autowired
    private BidDTOmapper bidDTOmapper;

    @Lazy
    @Autowired
    private CatDTOmapper catDTOmapper;

    @Lazy
    @Autowired
    private QueueEntryService qs;



    @Override //work in progress
    public BidEntryDetailsDTO apply(QueueEntry entry) {
        BidDTO bidDTO = bidDTOmapper.apply(entry);

        Run run = qs.getRunOfEntry(bidDTO.getId());
        RunDTO runDTO = run == null ? null : runDTOmapper.apply(run);

        Category cat = qs.getCatofEntry(bidDTO.getId());
        CatDTO catDTO = run == null ? null : catDTOmapper.apply(cat);

        return new BidEntryDetailsDTO(runDTO, bidDTO, catDTO);
    }
    
}
