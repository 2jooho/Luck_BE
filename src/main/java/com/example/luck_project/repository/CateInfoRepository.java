package com.example.luck_project.repository;

import com.example.luck_project.domain.CateEntity;
import com.example.luck_project.domain.PureInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CateInfoRepository extends JpaRepository<CateEntity, String> {
    Optional<List<CateEntity>> findByUseYnOrderByOrderNo(String useYn);
}
