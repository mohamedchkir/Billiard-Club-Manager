package org.example.bcm.core.model.entity;

import jakarta.persistence.*;
import lombok.*;

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

    @ManyToOne
    @JoinColumn(name = "table_type_id")
    private TableType tableType;

    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;

}