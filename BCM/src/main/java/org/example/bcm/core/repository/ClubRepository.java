package org.example.bcm.core.repository;

import org.example.bcm.core.model.entity.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClubRepository extends JpaRepository<Club, Long> {

    @Query("SELECT c FROM Club c WHERE (c.name LIKE %:name% OR :name IS NULL) AND (c.city.id = :CityId OR :CityId IS NULL)")
    List<Club> filter(String name, Long CityId);
}
