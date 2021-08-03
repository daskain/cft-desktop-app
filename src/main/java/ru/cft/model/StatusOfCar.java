package ru.cft.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
public enum StatusOfCar {

    FREE ("free"),
    RENTED ("rented"),
    UNAVAILABLE ("unavailable");

    private String status;

}
