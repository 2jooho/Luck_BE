package com.example.luck_project.repository;

import com.example.luck_project.domain.LuckWordImgEntity;
import com.example.luck_project.repository.querydsl.UserInfoRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LuckWordImgRepository extends JpaRepository<LuckWordImgEntity, String> {
    Optional<List<LuckWordImgEntity>> findByKoLuckWordNameIn(List<String> koLuckWordNameList);
}
