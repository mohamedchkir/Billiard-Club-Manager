package org.example.bcm.core.repository;

import org.example.bcm.core.model.entity.Table;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TableRepository extends JpaRepository<Table, Long> {
}
