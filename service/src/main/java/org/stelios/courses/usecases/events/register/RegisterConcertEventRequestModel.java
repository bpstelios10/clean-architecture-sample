package org.stelios.courses.usecases.events.register;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegisterConcertEventRequestModel {

    private String id;
    private String location;
    private Date date;
    private float ticketPrice;
    private int capacity;
    private int spotsLeft;
}
