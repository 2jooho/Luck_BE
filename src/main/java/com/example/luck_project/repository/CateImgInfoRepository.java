package com.example.luck_project.repository;

import com.example.luck_project.domain.CateEntity;
import com.example.luck_project.domain.CateImgEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CateImgInfoRepository extends JpaRepository<CateImgEntity, String> {
    Optional<List<CateImgEntity>> findByCateCodeInAndImageTypeAndImageoOrder(List<String> cateCdList, String imgType, String order);
}
