package org.stelios.courses.usecases.events.retrieve;

import java.util.List;

public interface IRetrieveConcertEventBoundary {

    List<RetrieveConcertEventResponseModel> getAll();
}
