package org.example.bcm.core.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

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
    @OneToMany(mappedBy = "service")
    private List<Club> clubs;

}
