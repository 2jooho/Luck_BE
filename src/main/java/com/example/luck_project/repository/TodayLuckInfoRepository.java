package com.example.luck_project.repository;

import com.example.luck_project.domain.TodayLuckEntity;
import com.example.luck_project.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TodayLuckInfoRepository extends JpaRepository<TodayLuckEntity, String> {


    Optional<TodayLuckEntity> findByUserIdAndYearLuckKrAndDayLuckKr(String userId, String year, String day);
}
