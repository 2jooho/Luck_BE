package com.example.luck_project.repository.querydsl;

import com.example.luck_project.domain.UserEntity;
import com.example.luck_project.dto.UserInfoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserInfoRepositoryCustom {
    Optional<UserInfoDto> searchAll(String userId);
}