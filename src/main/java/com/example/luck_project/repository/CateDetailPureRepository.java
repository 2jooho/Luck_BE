package com.example.luck_project.repository;

import com.example.luck_project.domain.BasicDateEntity;
import com.example.luck_project.domain.CateDetailPureEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CateDetailPureRepository extends JpaRepository<CateDetailPureEntity, String> {
    Optional<List<CateDetailPureEntity>> findByCateDetlCodeOrderByPureOrder(String cateDetlCd);
}
