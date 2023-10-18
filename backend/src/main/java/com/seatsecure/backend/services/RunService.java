package com.seatsecure.backend.services;

import java.util.List;

import com.seatsecure.backend.entities.Event;
import com.seatsecure.backend.entities.Run;

public interface RunService {

    Run getRunById(Long id);

    Event getEventOfRun(Long runId);

    List<Run> getRunsOfEvent(Long eventId);

    Event addNewRunToEvent(Long eventId, Run run);

    Run updateRun(Long id, Run newRunInfo);

    Run deleteRunById(Long runId);

}
