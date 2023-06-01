package org.stelios.courses.usecases.events.retrieve;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.stelios.courses.domain.events.IEvent;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class RetrieveConcertEventInteractor implements IRetrieveConcertEventBoundary {
    private final IRetrieveConcertEventGateway retrieveEventGateway;

    @Autowired
    public RetrieveConcertEventInteractor(IRetrieveConcertEventGateway retrieveEventGateway) {
        this.retrieveEventGateway = retrieveEventGateway;
    }

    @Override
    public List<RetrieveConcertEventResponseModel> getAll() {
        List<IEvent> allEvents = retrieveEventGateway.getAllEvents();

        return allEvents.stream()
                .map(e -> new RetrieveConcertEventResponseModel(e.getId(), e.getLocation(), e.getDate(), e.getTicketPrice(), e.getCapacity(), e.getSpotsLeft()))
                .collect(Collectors.toList());
    }
}
