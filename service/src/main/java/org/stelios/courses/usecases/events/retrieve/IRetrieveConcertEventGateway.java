package org.stelios.courses.usecases.events.retrieve;

import org.stelios.courses.domain.events.IEvent;

import java.util.List;

public interface IRetrieveConcertEventGateway {
    List<IEvent> getAllEvents();
}
