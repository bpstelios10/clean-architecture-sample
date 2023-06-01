package org.stelios.courses.adapter.gateways.h2.events;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.stelios.courses.adapter.repositories.events.ConcertEventJpaMapper;
import org.stelios.courses.adapter.repositories.events.IConcertEventRepository;
import org.stelios.courses.domain.events.ConcertEvent;
import org.stelios.courses.domain.events.IEvent;
import org.stelios.courses.domain.events.factories.IConcertEventFactory;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class H2GetConcertEventGatewayTest {

    private static final Date TEST_DATE = Date.from(Instant.now());

    @Mock
    private IConcertEventRepository repository;
    @Mock
    private IConcertEventFactory factory;
    @InjectMocks
    private H2RetrieveConcertEventGateway gateway;

    @Test
    void getAllEvents_returnsEmptyList_whenNoEventsExist() {
        when(repository.findAll()).thenReturn(List.of());

        List<IEvent> allEvents = gateway.getAllEvents();

        assertThat(allEvents).isEmpty();
    }

    @Test
    void getAllEvents_returnsEvents_whenEventsExist() {
        ConcertEventJpaMapper event1 = new ConcertEventJpaMapper("id1", "location1", TEST_DATE, 10.0f, 1000, 967);
        ConcertEventJpaMapper event2 = new ConcertEventJpaMapper("id2", "location2", TEST_DATE, 10.0f, 1000, 967);
        ConcertEvent concertEvent1 = new ConcertEvent("id1", "location1", TEST_DATE, 10.0f, 1000, 967);
        ConcertEvent concertEvent2 = new ConcertEvent("id2", "location2", TEST_DATE, 10.0f, 1000, 967);
        when(repository.findAll()).thenReturn(List.of(event1, event2));
        when(factory.create("id1", "location1", TEST_DATE, 10.0f, 1000, 967)).thenReturn(concertEvent1);
        when(factory.create("id2", "location2", TEST_DATE, 10.0f, 1000, 967)).thenReturn(concertEvent2);

        List<IEvent> allEvents = gateway.getAllEvents();

        assertThat(allEvents).containsExactly(concertEvent1, concertEvent2);
    }
}
