package com.seatsecure.backend.entities.DTO_mappers.simple;

import java.time.format.DateTimeFormatter;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.seatsecure.backend.entities.Run;
import com.seatsecure.backend.entities.DTOs.simple.RunDTO;

@Component
public class RunDTOmapper implements Function<Run, RunDTO>{

    @Override
    public RunDTO apply(Run run) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-YYYY HH:mm");
        return new RunDTO(run.getId(), run.getName(), run.getDescription(), formatter.format(run.getStartRunDate()),
        formatter.format(run.getEndRunDate()), formatter.format(run.getStartBidDate()), formatter.format(run.getEndBidDate()));
    }
    
}
