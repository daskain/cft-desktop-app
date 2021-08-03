package ru.cft.model;

import lombok.Data;

import javax.persistence.*;


@Entity
@Table(name = "cars")
@Data
public class Car {

    @Id
    @Column(name = "vin_car")
    private String vinCar;

    @Column(name = "brand_car")
    private String brandCar;

    @Column(name = "model_car")
    private String modelCar;

    @Column(name = "number_car")
    private String numberCar;

    @Column(name = "status")
    private String status;

    @OneToOne
    private Rental rental;

}
