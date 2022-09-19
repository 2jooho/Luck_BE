package com.example.luck_project.repository;

import com.example.luck_project.domain.MyFortuneInfoEntity;
import com.example.luck_project.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MyFortuneInfoRepository extends JpaRepository<MyFortuneInfoEntity, String> {

    List<MyFortuneInfoEntity> findAll();

    Optional<MyFortuneInfoEntity> findByUserId(String userId);
}
