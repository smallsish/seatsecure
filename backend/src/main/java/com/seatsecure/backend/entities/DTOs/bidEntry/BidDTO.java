package com.seatsecure.backend.entities.DTOs.bidEntry;


import com.seatsecure.backend.entities.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BidDTO {
    private Long id;
    private int numOfSeatsChosen;
    private Status status;
}
