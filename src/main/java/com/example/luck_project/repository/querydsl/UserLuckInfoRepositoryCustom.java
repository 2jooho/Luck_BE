package com.example.luck_project.repository.querydsl;

import com.example.luck_project.dto.UserLuckInfoDto;

import java.util.Optional;

public interface UserLuckInfoRepositoryCustom {
    Optional<UserLuckInfoDto> serchUserLuckInfo(String userId);
}