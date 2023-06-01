package org.stelios.courses.usecases.events.register;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.stelios.courses.adapter.repositories.events.EventAlreadyExistsException;
import org.stelios.courses.domain.events.IEvent;
import org.stelios.courses.domain.events.factories.IConcertEventFactory;

@Slf4j
@Component
public class RegisterConcertEventInteractor implements IRegisterConcertEventBoundary {

    private final IConcertEventFactory factory;
    private final IRegisterConcertEventGateway registerEventGateway;

    @Autowired
    public RegisterConcertEventInteractor(IConcertEventFactory factory, IRegisterConcertEventGateway registerEventGateway) {
        this.factory = factory;
        this.registerEventGateway = registerEventGateway;
    }

    @Override
    public RegisterConcertEventResponseModel save(RegisterConcertEventRequestModel requestModel) throws EventAlreadyExistsException {
        log.debug("received object: " + requestModel);

        if (registerEventGateway.eventAlreadyExists(requestModel.getId())) {
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

        registerEventGateway.save(event);

        return new RegisterConcertEventResponseModel(event.getId(), event.getLocation(), event.getDate(), event.getTicketPrice(), event.getCapacity(), event.getSpotsLeft());
    }
}
