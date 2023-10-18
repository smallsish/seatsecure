package com.seatsecure.backend.entities.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventVenueDTO{
    private String eventName;
    private String startDate;
    private String endDate;
    private String venueName;
}