package org.stelios.courses.domain.events.factories;

import org.stelios.courses.domain.events.IEvent;

import java.util.Date;

public interface IConcertEventFactory {

    IEvent create(String id, String location, Date date, float ticketPrice, int capacity, int spotsLeft);
}
