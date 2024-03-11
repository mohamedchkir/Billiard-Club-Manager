package org.example.bcm.core.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.bcm.shared.Enum.TableType;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Table {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TableType tableType;

    @Column(name = "tokens_needed")
    private Integer tokensNeeded;

    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;

    //todo check this relation
    @OneToMany(mappedBy = "table")
    private Set<Challenge> challenges;
}
