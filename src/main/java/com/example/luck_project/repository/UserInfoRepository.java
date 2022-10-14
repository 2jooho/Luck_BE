package com.example.luck_project.repository;

import com.example.luck_project.domain.UserEntity;
import com.example.luck_project.repository.querydsl.UserInfoRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserEntity, String>, UserInfoRepositoryCustom {

    List<UserEntity> findAll();

    Optional<UserEntity> findByUserId(String userId);
}
