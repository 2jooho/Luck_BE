package com.example.luck_project.repository;

import com.example.luck_project.domain.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<RefreshTokenEntity, Long> {

    boolean existsByRefreshToken(String token);
}
