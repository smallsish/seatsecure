package com.seatsecure.backend.entities.DTO_mappers.run;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.seatsecure.backend.entities.Run;
import com.seatsecure.backend.entities.DTOs.run.RunDTO;

@Component
public class RunDTOmapper implements Function<Run, RunDTO>{

    @Override
    public RunDTO apply(Run run) {
        DateFormat formatter = new SimpleDateFormat("dd-MM-YYYY HH:mm");
        return new RunDTO(run.getId(), run.getName(), run.getDescription(), formatter.format(run.getStartDate()), formatter.format(run.getEndDate()), formatter.format(run.getStartBidTime(), formatter.format(run.getEndBidTime())));
    }
    
}
