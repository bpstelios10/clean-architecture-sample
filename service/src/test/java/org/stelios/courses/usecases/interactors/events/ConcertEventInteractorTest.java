package org.stelios.courses.usecases.interactors.events;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.stelios.courses.domain.events.ConcertEvent;
import org.stelios.courses.domain.events.factories.ConcertEventFactory;
import org.stelios.courses.usecases.boundaries.events.IConcertEventRegisterGateway;
import org.stelios.courses.usecases.model.events.ConcertEventRequestModel;
import org.stelios.courses.usecases.model.events.ConcertEventResponseModel;

import java.time.Instant;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConcertEventInteractorTest {

    private static final Date TEST_DATE = Date.from(Instant.now());
    @Mock
    private ConcertEventFactory factory;
    @Mock
    private IConcertEventRegisterGateway registerGateway;

    @InjectMocks
    private ConcertEventInteractor concertEventInteractor;

    @Test
    void save() {
        ConcertEventRequestModel requestModel = new ConcertEventRequestModel("id", "location", TEST_DATE, 10.0f, 1000, 967);
        ConcertEventResponseModel expectedResponse = new ConcertEventResponseModel("id2", "location", TEST_DATE, 10.0f, 1000, 967);
        ConcertEvent eventFromFactory = new ConcertEvent("id2", "location", TEST_DATE, 10.0f, 1000, 967);
        when(factory.create("id", "location", TEST_DATE, 10.0f, 1000, 967))
                .thenReturn(eventFromFactory);
        doNothing().when(registerGateway).save(eventFromFactory);

        ConcertEventResponseModel actualResponse = concertEventInteractor.save(requestModel);

        assertThat(actualResponse).isEqualTo(expectedResponse);
    }
}
