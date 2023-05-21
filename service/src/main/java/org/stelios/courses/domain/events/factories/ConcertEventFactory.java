package org.stelios.courses.domain.events.factories;

import org.stelios.courses.domain.events.ConcertEvent;

import java.util.Date;

public class ConcertEventFactory implements IConcertEventFactory {

    @Override
    public ConcertEvent create(String id, String location, Date date, float ticketPrice, int capacity, int spotsLeft) {
        return new ConcertEvent(id, location, date, ticketPrice, capacity, spotsLeft);
    }
}
