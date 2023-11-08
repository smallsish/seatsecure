package com.seatsecure.backend.entities.DTOs.bidEntry;

import com.seatsecure.backend.entities.DTOs.cat.CatDTO;
import com.seatsecure.backend.entities.DTOs.run.RunDTO;
import com.seatsecure.backend.entities.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BidEntryDetailsDTO {

    private RunDTO run;
    private BidDTO bid;
    private CatDTO cat;
}
