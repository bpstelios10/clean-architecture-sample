package org.stelios.courses.adapter.gateways.h2.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.stelios.courses.adapter.repositories.events.ConcertEventJpaMapper;
import org.stelios.courses.adapter.repositories.events.IConcertEventRepository;
import org.stelios.courses.domain.events.IEvent;
import org.stelios.courses.usecases.boundaries.events.IConcertEventRegisterGateway;

@Component
public class ConcertEventCreationH2Gateway implements IConcertEventRegisterGateway {

    private final IConcertEventRepository repository;

    @Autowired
    public ConcertEventCreationH2Gateway(IConcertEventRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(IEvent event) {
        ConcertEventJpaMapper mapper =
                new ConcertEventJpaMapper(event.getId(), event.getLocation(), event.getDate(), event.getTicketPrice(), event.getCapacity(), event.getSpotsLeft());

        repository.save(mapper);
    }
}
