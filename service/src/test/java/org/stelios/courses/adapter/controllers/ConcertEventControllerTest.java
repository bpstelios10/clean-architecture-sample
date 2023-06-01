package org.stelios.courses.adapter.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.stelios.courses.adapter.repositories.events.EventAlreadyExistsException;
import org.stelios.courses.adapter.springweb.controllers.ConcertEventController;
import org.stelios.courses.usecases.events.register.IRegisterConcertEventBoundary;
import org.stelios.courses.usecases.events.register.RegisterConcertEventRequestModel;
import org.stelios.courses.usecases.events.register.RegisterConcertEventResponseModel;
import org.stelios.courses.usecases.events.retrieve.IRetrieveConcertEventBoundary;
import org.stelios.courses.usecases.events.retrieve.RetrieveConcertEventResponseModel;

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
    private IRegisterConcertEventBoundary registerEventBoundary;
    @Mock
    private IRetrieveConcertEventBoundary retrieveEventBoundary;
    @InjectMocks
    private ConcertEventController controller;

    @Test
    void getAllEvents_returnsAllExistingEvents() {
        List<RetrieveConcertEventResponseModel> allExistingEvents = List.of(
                new RetrieveConcertEventResponseModel("id1", "location1", TEST_DATE, 10.0f, 1000, 967),
                new RetrieveConcertEventResponseModel("id2", "location2", TEST_DATE, 10.0f, 1000, 967)
        );
        when(retrieveEventBoundary.getAll()).thenReturn(allExistingEvents);

        List<RetrieveConcertEventResponseModel> result = controller.getAllEvents();

        assertThat(result).containsExactlyElementsOf(allExistingEvents);
    }

    @Test
    void getAllEvents_returnsEmptyList_whenNoEvents() {
        when(retrieveEventBoundary.getAll()).thenReturn(new ArrayList<>());

        List<RetrieveConcertEventResponseModel> result = controller.getAllEvents();

        assertThat(result).isEmpty();
    }

    @Test
    void create_succeeds_whenNoIssues() throws EventAlreadyExistsException {
        RegisterConcertEventRequestModel requestModel = new RegisterConcertEventRequestModel("id", "location", TEST_DATE, 10.0f, 1000, 967);
        RegisterConcertEventResponseModel expectedResponse = new RegisterConcertEventResponseModel("id2", "location", TEST_DATE, 10.0f, 1000, 967);
        when(registerEventBoundary.save(requestModel)).thenReturn(expectedResponse);

        RegisterConcertEventResponseModel actualResponse = controller.create(requestModel);

        assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    void create_throwsException_whenBoundaryThrowsException() throws EventAlreadyExistsException {
        RegisterConcertEventRequestModel requestModel = new RegisterConcertEventRequestModel("id", "location", TEST_DATE, 10.0f, 1000, 967);
        when(registerEventBoundary.save(requestModel)).thenThrow(new EventAlreadyExistsException());

        assertThatThrownBy(() -> controller.create(requestModel))
                .isInstanceOf(EventAlreadyExistsException.class);
    }
}
