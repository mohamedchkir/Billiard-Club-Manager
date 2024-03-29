package org.example.bcm.core.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "city",cascade = CascadeType.ALL)
    private List<Club> clubs;

    @OneToMany(mappedBy = "city",cascade = CascadeType.ALL)
    private List<User> users;

}
