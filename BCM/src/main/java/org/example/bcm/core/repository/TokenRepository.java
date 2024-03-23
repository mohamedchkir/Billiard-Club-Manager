package org.example.bcm.core.repository;

import org.example.bcm.core.model.entity.Token;
import org.example.bcm.core.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface TokenRepository extends JpaRepository<Token,Long> {
    List<Token> findByUser(User user);

    Optional<Token> findByToken(String token);

    Optional<Token> findByUuid(UUID uuid);

    void deleteByUserId(Long userId);
}
