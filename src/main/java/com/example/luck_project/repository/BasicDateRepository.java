package com.example.luck_project.repository;

import com.example.luck_project.domain.BasicDateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BasicDateRepository extends JpaRepository<BasicDateEntity, String> {
    Optional<BasicDateEntity> findTop1ByOrderByIdDesc();

    //배치에서 사용
    Optional<BasicDateEntity> findTop1ByOrderByBasicDateDesc();
}
