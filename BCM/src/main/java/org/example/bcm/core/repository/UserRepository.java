package org.example.bcm.core.repository;

import org.example.bcm.core.model.dto.response.UserSimpleResponseDto;
import org.example.bcm.core.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u " +
            "JOIN FETCH u.role r " +
            "JOIN FETCH u.city c " +
            "WHERE (:firstName IS NULL OR u.firstName LIKE %:firstName%) " +
            "AND (:lastName IS NULL OR u.lastName LIKE %:lastName%) " +
            "AND (:cityId IS NULL OR c.id = :cityId) " +
            "AND r.name = 'CLIENT'")
    List<User> filterUsers(@Param("firstName") String firstName,
                                            @Param("lastName") String lastName,
                                            @Param("cityId") Long cityId);

    boolean existsByEmail(String email);
}

