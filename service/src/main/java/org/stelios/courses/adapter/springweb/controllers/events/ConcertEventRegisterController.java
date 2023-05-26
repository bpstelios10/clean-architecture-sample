package org.stelios.courses.adapter.springweb.controllers.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.stelios.courses.adapter.repositories.events.EventAlreadyExistsException;
import org.stelios.courses.usecases.boundaries.events.IConcertEventRegisterBoundary;
import org.stelios.courses.usecases.model.events.ConcertEventRequestModel;
import org.stelios.courses.usecases.model.events.ConcertEventResponseModel;

@Slf4j
@RestController
@RequestMapping("/events")
public class ConcertEventRegisterController {

    private final IConcertEventRegisterBoundary registerBoundary;

    @Autowired
    public ConcertEventRegisterController(IConcertEventRegisterBoundary registerBoundary) {
        this.registerBoundary = registerBoundary;
    }

    @PostMapping("/create")
    public ConcertEventResponseModel create(@RequestBody ConcertEventRequestModel requestModel) throws EventAlreadyExistsException {
        log.info("request:" + requestModel);

        return registerBoundary.save(requestModel);
    }
}
