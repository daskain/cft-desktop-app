CREATE TABLE IF NOT EXISTS users (
    login VARCHAR(25) PRIMARY KEY,
    password VARCHAR(25) NOT NULL,
    role VARCHAR(10)
)

CREATE TABLE IF NOT EXISTS cars (
    vin_car VARCHAR(25) PRIMARY KEY,
    brand_car VARCHAR(50) NOT NULL,
    model_car VARCHAR(50) NOT NULL,
    number_car VARCHAR(20) NOT NULL,
    status VARCHAR(20) DEFAULT 'free' CHECK (status in ('free', 'rented', 'unavailable'))
    );


CREATE TABLE IF NOT EXISTS clients (
    passport VARCHAR(25) PRIMARY KEY,
    last_name VARCHAR(50) NOT NULL,
    first_name VARCHAR(50) NOT null,
    patronymic VARCHAR(20),
    birthday DATE NOT NULL,
    active BOOLEAN NOT NULL
    );

CREATE TABLE IF NOT EXISTS rentals (
    id INTEGER IDENTITY PRIMARY KEY,
    vin_car VARCHAR(25) NOT NULL,
    passport VARCHAR(25) NOT NULL ,
    start_time_rental TIMESTAMP,
    contract VARCHAR(50) NOT NULL,
    active BOOLEAN NOT NULL,
    FOREIGN KEY (vin_car) REFERENCES cars(vin_car),
    FOREIGN KEY (passport) REFERENCES clients(passport)
    ON DELETE CASCADE
    );