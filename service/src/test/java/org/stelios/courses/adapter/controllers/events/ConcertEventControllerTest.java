package org.stelios.courses.adapter.controllers.events;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.stelios.courses.adapter.repositories.events.EventAlreadyExistsException;
import org.stelios.courses.adapter.springweb.controllers.events.ConcertEventController;
import org.stelios.courses.usecases.boundaries.events.IConcertEventBoundary;
import org.stelios.courses.usecases.model.events.ConcertEventRequestModel;
import org.stelios.courses.usecases.model.events.ConcertEventResponseModel;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConcertEventControllerTest {

    private static final Date TEST_DATE = Date.from(Instant.now());

    @Mock
    private IConcertEventBoundary eventBoundary;
    @InjectMocks
    private ConcertEventController controller;

    @Test
    void getAllEvents_returnsAllExistingEvents() {
        List<ConcertEventResponseModel> allExistingEvents = List.of(
                new ConcertEventResponseModel("id1", "location1", TEST_DATE, 10.0f, 1000, 967),
                new ConcertEventResponseModel("id2", "location2", TEST_DATE, 10.0f, 1000, 967)
        );
        when(eventBoundary.getAll()).thenReturn(allExistingEvents);

        List<ConcertEventResponseModel> result = controller.getAllEvents();

        assertThat(result).containsExactlyElementsOf(allExistingEvents);
    }

    @Test
    void getAllEvents_returnsEmptyList_whenNoEvents() {
        when(eventBoundary.getAll()).thenReturn(new ArrayList<>());

        List<ConcertEventResponseModel> result = controller.getAllEvents();

        assertThat(result).isEmpty();
    }

    @Test
    void create_succeeds_whenNoIssues() throws EventAlreadyExistsException {
        ConcertEventRequestModel requestModel = new ConcertEventRequestModel("id", "location", TEST_DATE, 10.0f, 1000, 967);
        ConcertEventResponseModel expectedResponse = new ConcertEventResponseModel("id2", "location", TEST_DATE, 10.0f, 1000, 967);
        when(eventBoundary.save(requestModel)).thenReturn(expectedResponse);

        ConcertEventResponseModel actualResponse = controller.create(requestModel);

        assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    void create_throwsException_whenBoundaryThrowsException() throws EventAlreadyExistsException {
        ConcertEventRequestModel requestModel = new ConcertEventRequestModel("id", "location", TEST_DATE, 10.0f, 1000, 967);
        when(eventBoundary.save(requestModel)).thenThrow(new EventAlreadyExistsException());

        assertThatThrownBy(() -> controller.create(requestModel))
                .isInstanceOf(EventAlreadyExistsException.class);
    }
}
