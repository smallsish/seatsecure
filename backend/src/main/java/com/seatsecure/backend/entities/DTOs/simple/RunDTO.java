package com.seatsecure.backend.entities.DTOs.simple;

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
}
