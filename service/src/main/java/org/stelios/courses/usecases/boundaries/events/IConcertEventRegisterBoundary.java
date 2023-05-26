package org.stelios.courses.usecases.boundaries.events;

import org.stelios.courses.adapter.repositories.events.EventAlreadyExistsException;
import org.stelios.courses.usecases.model.events.ConcertEventRequestModel;
import org.stelios.courses.usecases.model.events.ConcertEventResponseModel;

public interface IConcertEventRegisterBoundary {

    ConcertEventResponseModel save(ConcertEventRequestModel requestModel) throws EventAlreadyExistsException;
}
