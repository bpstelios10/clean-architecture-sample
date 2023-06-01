package org.stelios.courses.usecases.interactors.events.register;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.stelios.courses.adapter.repositories.events.EventAlreadyExistsException;
import org.stelios.courses.domain.events.ConcertEvent;
import org.stelios.courses.domain.events.factories.IConcertEventFactory;
import org.stelios.courses.usecases.events.register.IRegisterConcertEventGateway;
import org.stelios.courses.usecases.events.register.RegisterConcertEventInteractor;
import org.stelios.courses.usecases.events.register.RegisterConcertEventRequestModel;
import org.stelios.courses.usecases.events.register.RegisterConcertEventResponseModel;

import java.time.Instant;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegisterConcertEventInteractorTest {

    private static final Date TEST_DATE = Date.from(Instant.now());
    @Mock
    private IConcertEventFactory factory;
    @Mock
    private IRegisterConcertEventGateway registerEventGateway;

    @InjectMocks
    private RegisterConcertEventInteractor registerConcertEventInteractor;

    @Test
    void save_succeeds_whenEventDoesntExist() throws EventAlreadyExistsException {
        RegisterConcertEventRequestModel requestModel = new RegisterConcertEventRequestModel("id", "location", TEST_DATE, 10.0f, 1000, 967);
        RegisterConcertEventResponseModel expectedResponse = new RegisterConcertEventResponseModel("id2", "location", TEST_DATE, 10.0f, 1000, 967);
        ConcertEvent eventFromFactory = new ConcertEvent("id2", "location", TEST_DATE, 10.0f, 1000, 967);
        when(registerEventGateway.eventAlreadyExists("id")).thenReturn(false);
        when(factory.create("id", "location", TEST_DATE, 10.0f, 1000, 967))
                .thenReturn(eventFromFactory);
        doNothing().when(registerEventGateway).save(eventFromFactory);

        RegisterConcertEventResponseModel actualResponse = registerConcertEventInteractor.save(requestModel);

        assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    void save_throwsException_whenEventExists() {
        RegisterConcertEventRequestModel requestModel = new RegisterConcertEventRequestModel("id1", "location", TEST_DATE, 10.0f, 1000, 967);
        when(registerEventGateway.eventAlreadyExists("id1")).thenReturn(true);

        assertThatThrownBy(() -> registerConcertEventInteractor.save(requestModel))
                .isInstanceOf(EventAlreadyExistsException.class)
                .hasMessage("Trying to save existing event");
    }
}
