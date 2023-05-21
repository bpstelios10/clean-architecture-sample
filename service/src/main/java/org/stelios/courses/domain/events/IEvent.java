package org.stelios.courses.domain.events;

import java.util.Date;

public interface IEvent {
    String getId();

    String getLocation();

    Date getDate();

    Float getTicketPrice();

    int getCapacity();

    int getSpotsLeft();
}
