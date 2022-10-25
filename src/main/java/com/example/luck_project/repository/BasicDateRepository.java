package com.example.luck_project.repository;

import com.example.luck_project.domain.BasicDateEntity;
import com.example.luck_project.domain.UserLuckInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BasicDateRepository extends JpaRepository<BasicDateEntity, String> {
    Optional<BasicDateEntity> findTop1ByOrderByBasicDateDesc();
}
