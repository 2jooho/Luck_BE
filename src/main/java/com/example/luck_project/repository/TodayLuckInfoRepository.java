package com.example.luck_project.repository;

import com.example.luck_project.domain.UserEntity;
import com.example.luck_project.dto.TodayLuckInfoDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodayLuckInfoRepository extends JpaRepository<UserEntity, String> {

    List<UserEntity> findAll();
}
