package org.stelios.courses.usecases.interactors.events;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.stelios.courses.adapter.repositories.events.EventAlreadyExistsException;
import org.stelios.courses.domain.events.ConcertEvent;
import org.stelios.courses.domain.events.IEvent;
import org.stelios.courses.domain.events.factories.IConcertEventFactory;
import org.stelios.courses.usecases.boundaries.events.IConcertEventGateway;
import org.stelios.courses.usecases.model.events.ConcertEventRequestModel;
import org.stelios.courses.usecases.model.events.ConcertEventResponseModel;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConcertEventInteractorTest {

    private static final Date TEST_DATE = Date.from(Instant.now());
    @Mock
    private IConcertEventFactory factory;
    @Mock
    private IConcertEventGateway eventGateway;

    @InjectMocks
    private ConcertEventInteractor concertEventInteractor;

    @Test
    void getAll_returnsEmptyList_whenNoEvents() {
        when(eventGateway.getAllEvents()).thenReturn(List.of());

        List<ConcertEventResponseModel> allEvents = concertEventInteractor.getAll();

        assertThat(allEvents).isEmpty();
    }

    @Test
    void getAll_returnsAllAvailableEvents() {
        IEvent concertEvent1 = new ConcertEvent("id1", "location1", TEST_DATE, 10.0f, 1000, 967);
        IEvent concertEvent2 = new ConcertEvent("id2", "location2", TEST_DATE, 10.0f, 1000, 967);
        ConcertEventResponseModel concertEventResponse1 = new ConcertEventResponseModel("id1", "location1", TEST_DATE, 10.0f, 1000, 967);
        ConcertEventResponseModel concertEventResponse2 = new ConcertEventResponseModel("id2", "location2", TEST_DATE, 10.0f, 1000, 967);
        when(eventGateway.getAllEvents()).thenReturn(List.of(concertEvent1, concertEvent2));

        List<ConcertEventResponseModel> allEvents = concertEventInteractor.getAll();

        assertThat(allEvents).containsExactly(concertEventResponse1, concertEventResponse2);
    }

    @Test
    void save_succeeds_whenEventDoesntExist() throws EventAlreadyExistsException {
        ConcertEventRequestModel requestModel = new ConcertEventRequestModel("id", "location", TEST_DATE, 10.0f, 1000, 967);
        ConcertEventResponseModel expectedResponse = new ConcertEventResponseModel("id2", "location", TEST_DATE, 10.0f, 1000, 967);
        ConcertEvent eventFromFactory = new ConcertEvent("id2", "location", TEST_DATE, 10.0f, 1000, 967);
        when(eventGateway.eventAlreadyExists("id")).thenReturn(false);
        when(factory.create("id", "location", TEST_DATE, 10.0f, 1000, 967))
                .thenReturn(eventFromFactory);
        doNothing().when(eventGateway).save(eventFromFactory);

        ConcertEventResponseModel actualResponse = concertEventInteractor.save(requestModel);

        assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    void save_throwsException_whenEventExists() {
        ConcertEventRequestModel requestModel = new ConcertEventRequestModel("id1", "location", TEST_DATE, 10.0f, 1000, 967);
        when(eventGateway.eventAlreadyExists("id1")).thenReturn(true);

        assertThatThrownBy(() -> concertEventInteractor.save(requestModel))
                .isInstanceOf(EventAlreadyExistsException.class)
                .hasMessage("Trying to save existing event");
    }
}
