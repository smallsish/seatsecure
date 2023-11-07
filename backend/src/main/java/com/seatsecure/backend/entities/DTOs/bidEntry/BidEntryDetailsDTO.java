package com.seatsecure.backend.entities.DTOs.bidEntry;

import com.seatsecure.backend.entities.DTOs.run.RunDTO;
import com.seatsecure.backend.entities.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BidEntryDetailsDTO {

    private Long id;
    private RunDTO run;
    private String categoryName;
    private int numOfSeatsChosen;
    private Status status;
}
