package com.example.luck_project.repository;

import com.example.luck_project.domain.UserLuckInfoEntity;
import com.example.luck_project.repository.querydsl.UserLuckInfoRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserLuckInfoRepository extends JpaRepository<UserLuckInfoEntity, String>, UserLuckInfoRepositoryCustom {
    Optional<UserLuckInfoEntity> findByUserId(String userId);

}
