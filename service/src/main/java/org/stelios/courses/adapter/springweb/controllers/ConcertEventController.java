package org.stelios.courses.adapter.springweb.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.stelios.courses.adapter.repositories.events.EventAlreadyExistsException;
import org.stelios.courses.usecases.events.register.IRegisterConcertEventBoundary;
import org.stelios.courses.usecases.events.register.RegisterConcertEventRequestModel;
import org.stelios.courses.usecases.events.register.RegisterConcertEventResponseModel;
import org.stelios.courses.usecases.events.retrieve.IRetrieveConcertEventBoundary;
import org.stelios.courses.usecases.events.retrieve.RetrieveConcertEventResponseModel;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/events")
public class ConcertEventController {

    private final IRegisterConcertEventBoundary registerEventBoundary;
    private final IRetrieveConcertEventBoundary retrieveEventBoundary;

    @Autowired
    public ConcertEventController(IRegisterConcertEventBoundary registerEventBoundary, IRetrieveConcertEventBoundary retrieveEventBoundary) {
        this.registerEventBoundary = registerEventBoundary;
        this.retrieveEventBoundary = retrieveEventBoundary;
    }

    @GetMapping
    public List<RetrieveConcertEventResponseModel> getAllEvents() {
        log.info("request for all events");

        return retrieveEventBoundary.getAll();
    }

    @PostMapping("/create")
    public RegisterConcertEventResponseModel create(@RequestBody RegisterConcertEventRequestModel requestModel) throws EventAlreadyExistsException {
        log.info("request:" + requestModel);

        return registerEventBoundary.save(requestModel);
    }
}
