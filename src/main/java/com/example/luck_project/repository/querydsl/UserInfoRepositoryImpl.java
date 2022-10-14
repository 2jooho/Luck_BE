package com.example.luck_project.repository.querydsl;

import com.example.luck_project.domain.UserEntity;
import com.example.luck_project.dto.UserInfoDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.example.luck_project.domain.QUserEntity.userEntity;
import static com.example.luck_project.domain.QUserLuckInfoEntity.userLuckInfoEntity;

@RequiredArgsConstructor
public class UserInfoRepositoryImpl implements UserInfoRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<UserInfoDto> searchAll(String userId) {
        UserInfoDto result = queryFactory
                .select(Projections.constructor(UserInfoDto.class,
                        userEntity.userId,
                        userEntity.userName,
                        userEntity.birth,
                        userEntity.birthFlag,
                        userLuckInfoEntity.yearBtm,
                        userLuckInfoEntity.dayBtm
                        ))
                .from(userEntity)
                .innerJoin(userLuckInfoEntity).on(userEntity.userId.eq(userLuckInfoEntity.userId))
                .where(userEntity.userId.eq(userId))
                .fetchOne();

        return Optional.ofNullable(result);
    }

}