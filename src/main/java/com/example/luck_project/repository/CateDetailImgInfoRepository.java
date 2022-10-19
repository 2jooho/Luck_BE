package com.example.luck_project.repository;

import com.example.luck_project.domain.CateDetailImgEntity;
import com.example.luck_project.domain.CateImgEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CateDetailImgInfoRepository extends JpaRepository<CateDetailImgEntity, String> {
    Optional<List<CateDetailImgEntity>> findByCateDetlCdInAndImageTypeAndImageoOrder(List<String> cateDetlCdList, String imgType, String order);
}
