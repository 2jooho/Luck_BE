package com.example.luck_project.batch.repository;


import com.example.luck_project.batch.entity.FcmAuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FcmAuthRepository extends JpaRepository<FcmAuthEntity, String> {
    Optional<FcmAuthEntity> findTop1ByOrderByIdDesc();
}
