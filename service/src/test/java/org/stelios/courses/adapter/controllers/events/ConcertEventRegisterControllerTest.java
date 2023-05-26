package org.stelios.courses.adapter.controllers.events;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.stelios.courses.adapter.repositories.events.EventAlreadyExistsException;
import org.stelios.courses.adapter.springweb.controllers.events.ConcertEventRegisterController;
import org.stelios.courses.usecases.boundaries.events.IConcertEventRegisterBoundary;
import org.stelios.courses.usecases.model.events.ConcertEventRequestModel;
import org.stelios.courses.usecases.model.events.ConcertEventResponseModel;

import java.time.Instant;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConcertEventRegisterControllerTest {

    private static final Date TEST_DATE = Date.from(Instant.now());

    @Mock
    private IConcertEventRegisterBoundary registerBoundary;
    @InjectMocks
    private ConcertEventRegisterController controller;

    @Test
    void create_succeeds_whenNoIssues() throws EventAlreadyExistsException {
        ConcertEventRequestModel requestModel = new ConcertEventRequestModel("id", "location", TEST_DATE, 10.0f, 1000, 967);
        ConcertEventResponseModel expectedResponse = new ConcertEventResponseModel("id2", "location", TEST_DATE, 10.0f, 1000, 967);
        when(registerBoundary.save(requestModel)).thenReturn(expectedResponse);

        ConcertEventResponseModel actualResponse = controller.create(requestModel);

        assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    void create_throwsException_whenBoundaryThrowsException() throws EventAlreadyExistsException {
        ConcertEventRequestModel requestModel = new ConcertEventRequestModel("id", "location", TEST_DATE, 10.0f, 1000, 967);
        when(registerBoundary.save(requestModel)).thenThrow(new EventAlreadyExistsException());

        assertThatThrownBy(() -> controller.create(requestModel))
                .isInstanceOf(EventAlreadyExistsException.class);
    }
}
