package com.seatsecure.backend.entities.DTOs.run;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RunDTO {
    private Long id;
    private String name;
    private String description;
    private String startDate;
    private String endDate;
    private LocalDateTime startBidTime;
    private LocalDateTime endBidTime;
}
