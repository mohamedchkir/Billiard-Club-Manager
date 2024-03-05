package org.example.bcm.core.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String address;
    private LocalTime openingHour;
    private LocalTime closeHour;
    private int numberOfToken;

    @ManyToOne
    @JoinColumn(name = "City_id")
    private City city;

    @ManyToMany
    @JoinTable(
            name = "club_service",
            joinColumns = @JoinColumn(name = "club_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id"))
    private Set<Service> services;

    @OneToMany(mappedBy = "club")
    private Set<Table> tables;

}
