package org.example.bcm.core.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Service {
    @Id
    private Long id;
    private String name;
    @Column(name = "image_url")
    private String imageUrl;

    @ManyToMany(mappedBy = "services")
    private Set<Club> clubs;

}
