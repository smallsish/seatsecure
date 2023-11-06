package com.seatsecure.backend.entities.DTOs.event;

import java.util.List;

import com.seatsecure.backend.entities.DTOs.run.RunDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EventRunsDTO{
    private EventDTO event;
    private List<RunDTO> runs;
}
