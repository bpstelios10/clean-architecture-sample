package org.stelios.courses.adapter.springweb.controllers.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.stelios.courses.adapter.repositories.events.EventAlreadyExistsException;
import org.stelios.courses.usecases.boundaries.events.IConcertEventBoundary;
import org.stelios.courses.usecases.model.events.ConcertEventRequestModel;
import org.stelios.courses.usecases.model.events.ConcertEventResponseModel;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/events")
public class ConcertEventController {

    private final IConcertEventBoundary eventBoundary;

    @Autowired
    public ConcertEventController(IConcertEventBoundary eventBoundary) {
        this.eventBoundary = eventBoundary;
    }

    @GetMapping
    public List<ConcertEventResponseModel> getAllEvents() {
        log.info("request for all events");

        return eventBoundary.getAll();
    }

    @PostMapping("/create")
    public ConcertEventResponseModel create(@RequestBody ConcertEventRequestModel requestModel) throws EventAlreadyExistsException {
        log.info("request:" + requestModel);

        return eventBoundary.save(requestModel);
    }
}
