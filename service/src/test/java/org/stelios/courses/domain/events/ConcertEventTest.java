package org.stelios.courses.domain.events;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

class ConcertEventTest {

    private ConcertEvent concertEvent;
    private static final Date TEST_DATE = Date.from(Instant.now());

    @BeforeEach
    void setUp() {
        concertEvent = new ConcertEvent("id", "location", TEST_DATE, 10.0f, 1000, 967);
    }

    @Test
    void getId_returnsCorrectValue() {
        assertThat(concertEvent.getId()).isEqualTo("id");
    }

    @Test
    void getLocation_returnsCorrectValue() {
        assertThat(concertEvent.getLocation()).isEqualTo("location");
    }

    @Test
    void getDate_returnsCorrectValue() {
        assertThat(concertEvent.getDate()).isEqualTo(TEST_DATE);
    }

    @Test
    void getTicketPrice_returnsCorrectValue() {
        assertThat(concertEvent.getTicketPrice()).isEqualTo(10f);
    }

    @Test
    void getCapacity_returnsCorrectValue() {
        assertThat(concertEvent.getCapacity()).isEqualTo(1000);
    }

    @Test
    void getSpotsLeft_returnsCorrectValue() {
        assertThat(concertEvent.getSpotsLeft()).isEqualTo(967);
    }
}
