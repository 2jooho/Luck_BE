package com.example.luck_project.repository;

import com.example.luck_project.domain.UserLoginHistoryEntity;
import com.example.luck_project.domain.UserMobileDeviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserMobileDeviceRepository extends JpaRepository<UserMobileDeviceEntity, String> {
    /**
     * 사용자 디바이스 정보 조회
     * @param userId
     * @return
     */
    Optional<UserMobileDeviceEntity> findTop1ByUserIdOrderByIdDesc(String userId);


}
