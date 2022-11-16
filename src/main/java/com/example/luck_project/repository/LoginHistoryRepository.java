package com.example.luck_project.repository;

import com.example.luck_project.domain.UserLoginHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginHistoryRepository extends JpaRepository<UserLoginHistoryEntity, String> {
    /**
     * 로그인 히스토리 개수 조회
     * @param userId
     * @param isCode
     * @return
     */
    Optional<Integer> countByUserIdAndLoginIscode(String userId, String isCode);

    /**
     * 로그인 정보 조회
     * @param userId
     * @param isCode
     * @return
     */
    Optional<UserLoginHistoryEntity> findByUserIdAndLoginIscode(String userId, String isCode);

}
