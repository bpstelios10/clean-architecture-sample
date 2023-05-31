package org.stelios.courses.usecases.interactors.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.stelios.courses.adapter.repositories.events.EventAlreadyExistsException;
import org.stelios.courses.domain.events.IEvent;
import org.stelios.courses.domain.events.factories.IConcertEventFactory;
import org.stelios.courses.usecases.boundaries.events.IConcertEventBoundary;
import org.stelios.courses.usecases.boundaries.events.IConcertEventGateway;
import org.stelios.courses.usecases.model.events.ConcertEventRequestModel;
import org.stelios.courses.usecases.model.events.ConcertEventResponseModel;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ConcertEventInteractor implements IConcertEventBoundary {

    private final IConcertEventFactory factory;
    private final IConcertEventGateway eventGateway;

    @Autowired
    public ConcertEventInteractor(IConcertEventFactory factory, IConcertEventGateway eventGateway) {
        this.factory = factory;
        this.eventGateway = eventGateway;
    }

    @Override
    public List<ConcertEventResponseModel> getAll() {
        List<IEvent> allEvents = eventGateway.getAllEvents();

        return allEvents.stream()
                .map(e -> new ConcertEventResponseModel(e.getId(), e.getLocation(), e.getDate(), e.getTicketPrice(), e.getCapacity(), e.getSpotsLeft()))
                .collect(Collectors.toList());
    }

    @Override
    public ConcertEventResponseModel save(ConcertEventRequestModel requestModel) throws EventAlreadyExistsException {
        log.debug("received object: " + requestModel);

        if (eventGateway.eventAlreadyExists(requestModel.getId())) {
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

        eventGateway.save(event);

        return new ConcertEventResponseModel(event.getId(), event.getLocation(), event.getDate(), event.getTicketPrice(), event.getCapacity(), event.getSpotsLeft());
    }
}
