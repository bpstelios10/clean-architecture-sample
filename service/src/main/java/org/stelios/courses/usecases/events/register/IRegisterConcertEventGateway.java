package org.stelios.courses.usecases.events.register;

import org.stelios.courses.domain.events.IEvent;

public interface IRegisterConcertEventGateway {

    boolean eventAlreadyExists(String id);

    void save(IEvent event);
}
