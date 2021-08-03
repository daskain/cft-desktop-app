package ru.cft.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "clients")
@Data
public class Client {

    @Id
    @Column(name = "passport")
    private String passport;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "patronymic")
    private String patronymic;

    @Column(name = "birthday")
    private Date birthday;

    @Getter(AccessLevel.NONE)
    @Column(name = "active")
    private Boolean active;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Rental> rental;

    public Boolean isActive()
    {
        return active;
    }
}
