package com.example.luck_project.repository;

import com.example.luck_project.domain.UserEntity;
import com.example.luck_project.domain.UserLuckInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserLuckInfoRepository extends JpaRepository<UserLuckInfoEntity, String> {

    List<UserLuckInfoEntity> findAll();

    Optional<UserLuckInfoEntity> findByUserId(String userId);
}
