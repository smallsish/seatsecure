package com.seatsecure.backend.entities.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
