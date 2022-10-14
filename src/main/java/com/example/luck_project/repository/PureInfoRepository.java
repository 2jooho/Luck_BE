package com.example.luck_project.repository;

import com.example.luck_project.domain.PureInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PureInfoRepository extends JpaRepository<PureInfoEntity, String> {
    Optional<PureInfoEntity> findByPureCnctnIn(List<String> pureCnctnList);
}
