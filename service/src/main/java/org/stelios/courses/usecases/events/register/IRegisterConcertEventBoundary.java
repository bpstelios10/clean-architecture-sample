package org.stelios.courses.usecases.events.register;

import org.stelios.courses.adapter.repositories.events.EventAlreadyExistsException;

public interface IRegisterConcertEventBoundary {

    RegisterConcertEventResponseModel save(RegisterConcertEventRequestModel requestModel) throws EventAlreadyExistsException;
}
