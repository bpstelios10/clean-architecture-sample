package org.stelios.courses.usecases.boundaries.events;

import org.stelios.courses.domain.events.IEvent;

public interface IConcertEventRegisterGateway {

    boolean eventAlreadyExists(String id);

    void save(IEvent event);
}
