package org.stelios.courses.usecases.interactors.events.retrieve;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.stelios.courses.domain.events.ConcertEvent;
import org.stelios.courses.domain.events.IEvent;
import org.stelios.courses.usecases.events.retrieve.IRetrieveConcertEventGateway;
import org.stelios.courses.usecases.events.retrieve.RetrieveConcertEventInteractor;
import org.stelios.courses.usecases.events.retrieve.RetrieveConcertEventResponseModel;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RetrieveConcertEventInteractorTest {

    private static final Date TEST_DATE = Date.from(Instant.now());
    @Mock
    private IRetrieveConcertEventGateway retrieveEventGateway;

    @InjectMocks
    private RetrieveConcertEventInteractor retrieveConcertEventInteractor;

    @Test
    void getAll_returnsEmptyList_whenNoEvents() {
        when(retrieveEventGateway.getAllEvents()).thenReturn(List.of());

        List<RetrieveConcertEventResponseModel> allEvents = retrieveConcertEventInteractor.getAll();

        assertThat(allEvents).isEmpty();
    }

    @Test
    void getAll_returnsAllAvailableEvents() {
        IEvent concertEvent1 = new ConcertEvent("id1", "location1", TEST_DATE, 10.0f, 1000, 967);
        IEvent concertEvent2 = new ConcertEvent("id2", "location2", TEST_DATE, 10.0f, 1000, 967);
        RetrieveConcertEventResponseModel concertEventResponse1 = new RetrieveConcertEventResponseModel("id1", "location1", TEST_DATE, 10.0f, 1000, 967);
        RetrieveConcertEventResponseModel concertEventResponse2 = new RetrieveConcertEventResponseModel("id2", "location2", TEST_DATE, 10.0f, 1000, 967);
        when(retrieveEventGateway.getAllEvents()).thenReturn(List.of(concertEvent1, concertEvent2));

        List<RetrieveConcertEventResponseModel> allEvents = retrieveConcertEventInteractor.getAll();

        assertThat(allEvents).containsExactly(concertEventResponse1, concertEventResponse2);
    }
}
