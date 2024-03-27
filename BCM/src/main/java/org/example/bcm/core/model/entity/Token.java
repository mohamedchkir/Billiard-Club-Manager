package org.example.bcm.core.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.bcm.shared.Enum.TokenType;

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

    @Enumerated(EnumType.STRING)
    private TokenType type;

    @ManyToOne
    private User user;
}
