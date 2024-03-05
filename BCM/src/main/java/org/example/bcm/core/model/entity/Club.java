package org.example.bcm.core.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

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
    private String fbLink;

    private LocalDate openingHour;
    private LocalDate closeHour;
    private int numberOfToken;

    @ManyToOne
    @JoinColumn(name = "City_id")
    private City city;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service service;
}
