package com.example.luck_project.repository;

import com.example.luck_project.domain.LuckCalendaEntity;
import com.example.luck_project.domain.UserLuckInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserLuckInfoRepository extends JpaRepository<UserLuckInfoEntity, String> {
    Optional<UserLuckInfoEntity> findByUserId(String userId);

}
