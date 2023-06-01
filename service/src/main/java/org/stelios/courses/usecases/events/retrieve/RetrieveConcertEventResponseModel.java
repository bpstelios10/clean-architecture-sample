package org.stelios.courses.usecases.events.retrieve;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class RetrieveConcertEventResponseModel {

    private String id;
    private String location;
    private Date date;
    private float ticketPrice;
    private int capacity;
    private int spotsLeft;
}
