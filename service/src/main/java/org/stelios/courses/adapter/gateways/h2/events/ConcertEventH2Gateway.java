package org.stelios.courses.adapter.gateways.h2.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.stelios.courses.adapter.repositories.events.ConcertEventJpaMapper;
import org.stelios.courses.adapter.repositories.events.IConcertEventRepository;
import org.stelios.courses.domain.events.IEvent;
import org.stelios.courses.domain.events.factories.IConcertEventFactory;
import org.stelios.courses.usecases.boundaries.events.IConcertEventGateway;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ConcertEventH2Gateway implements IConcertEventGateway {

    private final IConcertEventRepository repository;
    private final IConcertEventFactory factory;

    @Autowired
    public ConcertEventH2Gateway(IConcertEventRepository repository, IConcertEventFactory factory) {
        this.repository = repository;
        this.factory = factory;
    }

    @Override
    public List<IEvent> getAllEvents() {
        List<ConcertEventJpaMapper> repositoryEvents = repository.findAll();

        return repositoryEvents.stream()
                .map(e -> factory.create(e.getId(), e.getLocation(), e.getDate(), e.getTicketPrice(), e.getCapacity(), e.getSpotsLeft()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean eventAlreadyExists(String id) {
        return repository.findById(id).isPresent();
    }

    @Override
    public void save(IEvent event) {
        ConcertEventJpaMapper mapper =
                new ConcertEventJpaMapper(event.getId(), event.getLocation(), event.getDate(), event.getTicketPrice(), event.getCapacity(), event.getSpotsLeft());

        repository.save(mapper);
    }
}
