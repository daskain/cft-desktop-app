package ru.cft.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.extern.apachecommons.CommonsLog;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "rentals")
@Data
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "passport")
    private Client client;

    @OneToOne
    @JoinColumn(name = "vin_car")
    private Car car;

    @Column(name = "start_time_rental")
    private Timestamp startTimeRental;

    @Column(name = "contract")
    private String contract;

    @Getter(AccessLevel.NONE)
    @Column(name = "active")
    private Boolean active;

    public Boolean isActive()
    {
        return active;
    }
}
