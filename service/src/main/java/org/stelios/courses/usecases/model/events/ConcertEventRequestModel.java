package org.stelios.courses.usecases.model.events;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class ConcertEventRequestModel {

    private String id;
    private String location;
    private Date date;
    private float ticketPrice;
    private int capacity;
    private int spotsLeft;
}
