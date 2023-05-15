package com.example.luck_project.repository;

import com.example.luck_project.domain.UserEntity;
import com.example.luck_project.domain.UserRecommandStarEntity;
import com.example.luck_project.repository.querydsl.UserInfoRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRecommandStarRepository extends JpaRepository<UserRecommandStarEntity, String> {
    /**
     * 추천인 코드 개수 조회
     * @param userEntity
     * @return
     */
    Optional<Integer> countByUserEntity(UserEntity userEntity);

}
