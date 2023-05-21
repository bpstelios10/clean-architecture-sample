package org.stelios.courses.domain.events;

import java.util.Date;

public class ConcertEvent implements IEvent {

    private final String id;
    private final String location;
    private final Date date;
    private final float ticketPrice;
    private final int capacity;
    private final int spotsLeft;

    public ConcertEvent(String id, String location, Date date, float ticketPrice, int capacity, int spotsLeft) {
        this.id = id;
        this.location = location;
        this.date = date;
        this.ticketPrice = ticketPrice;
        this.capacity = capacity;
        this.spotsLeft = spotsLeft;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getLocation() {
        return location;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public Float getTicketPrice() {
        return ticketPrice;
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    @Override
    public int getSpotsLeft() {
        return spotsLeft;
    }
}
