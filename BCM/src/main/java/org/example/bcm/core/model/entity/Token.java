package org.example.bcm.core.model.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean revoked;
    private Instant expiryDate;

    @Column(unique = true)
    private UUID uuid;

    @Column(unique = true)
    private String token;

    @ManyToOne
    private User user;
}
