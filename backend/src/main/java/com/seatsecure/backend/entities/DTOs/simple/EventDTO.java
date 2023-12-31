package com.seatsecure.backend.entities.DTOs.simple;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EventDTO {
    private Long id;
    private String eventName;
    private String eventDesc;
    private String startDate;
    private String endDate;
}
