package org.stelios.courses.usecases.interactors.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.stelios.courses.adapter.repositories.events.EventAlreadyExistsException;
import org.stelios.courses.domain.events.IEvent;
import org.stelios.courses.domain.events.factories.ConcertEventFactory;
import org.stelios.courses.usecases.boundaries.events.IConcertEventRegisterBoundary;
import org.stelios.courses.usecases.boundaries.events.IConcertEventRegisterGateway;
import org.stelios.courses.usecases.model.events.ConcertEventRequestModel;
import org.stelios.courses.usecases.model.events.ConcertEventResponseModel;

@Slf4j
@Component
public class ConcertEventInteractor implements IConcertEventRegisterBoundary {

    private final ConcertEventFactory factory;
    private final IConcertEventRegisterGateway registerGateway;

    @Autowired
    public ConcertEventInteractor(ConcertEventFactory factory, IConcertEventRegisterGateway registerGateway) {
        this.factory = factory;
        this.registerGateway = registerGateway;
    }

    @Override
    public ConcertEventResponseModel save(ConcertEventRequestModel requestModel) throws EventAlreadyExistsException {
        log.debug("received object: " + requestModel);

        if (registerGateway.eventAlreadyExists(requestModel.getId())) {
            throw new EventAlreadyExistsException();
        }

        IEvent event = factory.create(
                requestModel.getId(),
                requestModel.getLocation(),
                requestModel.getDate(),
                requestModel.getTicketPrice(),
                requestModel.getCapacity(),
                requestModel.getSpotsLeft()
        );

        registerGateway.save(event);

        return new ConcertEventResponseModel(event.getId(), event.getLocation(), event.getDate(), event.getTicketPrice(), event.getCapacity(), event.getSpotsLeft());
    }
}
