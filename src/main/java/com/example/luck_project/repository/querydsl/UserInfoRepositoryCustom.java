package com.example.luck_project.repository.querydsl;

import com.example.luck_project.dto.UserInfoDto;

import java.util.Optional;

public interface UserInfoRepositoryCustom {
    Optional<UserInfoDto> serchUserInfo(String userId);
}