package com.example.luck_project.repository;

import com.example.luck_project.domain.UserPaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserPaymentInfoRepository extends JpaRepository<UserPaymentEntity, String> {
    Optional<UserPaymentEntity> findByUserId(String userId);
}
