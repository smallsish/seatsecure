package com.seatsecure.backend.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventVenueDTO{
    private String eventName;
    private Date startDate;
    private Date endDate;
    private String venueName;
}