package com.example.luck_project.repository;

import com.example.luck_project.domain.CateDetailEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CateDetailInfoRepository extends JpaRepository<CateDetailEntity, String> {
    Optional<List<CateDetailEntity>> findTop8ByUseYnAndAdYnAndCateCdInOrderByInqryCntDesc(String useYn, String adYn, List<String> cateCdList);
    Optional<List<CateDetailEntity>> findTop8ByUseYnOrderByAdYnDescInqryCntDesc(String useYn);
    Optional<Page<CateDetailEntity>> findByCateCdOrderByInqryCntDescOrderNo(String cateCd, Pageable pageable);

    Optional<CateDetailEntity> findByCateCdAndCateDetlCd(String cateCd, String cateDetailCd);

}
