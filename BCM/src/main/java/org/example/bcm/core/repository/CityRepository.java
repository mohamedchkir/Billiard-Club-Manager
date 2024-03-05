package org.example.bcm.core.repository;

import org.example.bcm.core.model.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {
}
