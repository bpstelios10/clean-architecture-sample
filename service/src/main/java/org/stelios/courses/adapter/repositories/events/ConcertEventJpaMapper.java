package org.stelios.courses.adapter.repositories.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "ConcertEvent")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConcertEventJpaMapper {
    @Id
    private String id;
    private String location;
    private Date date;
    private float ticketPrice;
    private int capacity;
    private int spotsLeft;
}
