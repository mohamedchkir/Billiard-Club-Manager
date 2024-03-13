package org.example.bcm.core.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Challenge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "challenger_id")
    private User challenger;

    @ManyToOne
    @JoinColumn(name = "adversary_id")
    private User adversary;

    @ManyToOne(optional = true)
    @JoinColumn(name = "winner_id")
    private User winner;

    @ManyToOne
    @JoinColumn(name = "table_id")
    private Table table;

    private Integer numberOfParties;
}