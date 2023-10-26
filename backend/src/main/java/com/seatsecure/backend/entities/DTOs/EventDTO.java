package com.seatsecure.backend.entities.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EventDTO {
    private Long id;
    private String eventName;
    private String startDate;
    private String endDate;
}
