package org.stelios.courses.domain.events.factories;

import org.junit.jupiter.api.Test;
import org.stelios.courses.domain.events.ConcertEvent;
import org.stelios.courses.domain.events.IEvent;

import java.time.Instant;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

class ConcertEventFactoryTest {

    private static final Date TEST_DATE = Date.from(Instant.now());
    private final ConcertEventFactory factory = new ConcertEventFactory();

    @Test
    void create_createsCorrectObject() {
        IEvent factoryEvent = factory.create("id1", "location1", TEST_DATE, 10.1f, 1001, 977);
        ConcertEvent expectedEvent = new ConcertEvent("id1", "location1", TEST_DATE, 10.1f, 1001, 977);

        assertThat(factoryEvent).isEqualTo(expectedEvent);
    }
}