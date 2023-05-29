package org.stelios.courses.usecases.boundaries.events;

import org.stelios.courses.domain.events.IEvent;

import java.util.List;

public interface IConcertEventGateway {
    List<IEvent> getAllEvents();

    boolean eventAlreadyExists(String id);

    void save(IEvent event);
}
