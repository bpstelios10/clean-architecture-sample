package org.stelios.courses.usecases.boundaries.events;

import org.stelios.courses.domain.events.IEvent;

public interface IConcertEventRegisterGateway {

    void save(IEvent event);
}
