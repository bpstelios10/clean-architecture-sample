package org.stelios.courses.adapter.gateways.h2.events;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.stelios.courses.adapter.repositories.events.ConcertEventJpaMapper;
import org.stelios.courses.adapter.repositories.events.IConcertEventRepository;
import org.stelios.courses.domain.events.ConcertEvent;
import org.stelios.courses.domain.events.IEvent;

import java.time.Instant;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConcertEventCreationH2GatewayTest {

    private static final Date TEST_DATE = Date.from(Instant.now());

    @Captor
    ArgumentCaptor<ConcertEventJpaMapper> mapper;
    @Mock
    private IConcertEventRepository repository;
    @InjectMocks
    private ConcertEventCreationH2Gateway gateway;

    @Test
    void save() {
        IEvent event = new ConcertEvent("id", "location", TEST_DATE, 10.0f, 1000, 967);
        when(repository.save(mapper.capture())).thenReturn(null);

        gateway.save(event);

        ConcertEventJpaMapper capturedMapper = mapper.getValue();
        assertThat(capturedMapper).isEqualTo(
                new ConcertEventJpaMapper(event.getId(), event.getLocation(), event.getDate(), event.getTicketPrice(), event.getCapacity(), event.getSpotsLeft()));
        verify(repository).save(capturedMapper);
    }
}
