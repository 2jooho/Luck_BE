package com.example.luck_project.batch.repository;

import com.example.luck_project.batch.entity.PushMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PushMessageRepository extends JpaRepository<PushMessageEntity, String> {
    Optional<PushMessageEntity> findByTimeLuck(String timeLuck);
}
