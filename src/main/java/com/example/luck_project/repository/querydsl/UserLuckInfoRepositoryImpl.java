package com.example.luck_project.repository.querydsl;

import com.example.luck_project.domain.UserLuckInfoEntity;
import com.example.luck_project.dto.UserInfoDto;
import com.example.luck_project.dto.UserLuckInfoDto;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.example.luck_project.domain.QUserLuckInfoEntity.userLuckInfoEntity;
import static com.example.luck_project.domain.QLuckWordImgEntity.luckWordImgEntity;

@RequiredArgsConstructor
public class UserLuckInfoRepositoryImpl implements UserLuckInfoRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<UserLuckInfoDto> serchUserLuckInfo(String userId) {
        UserLuckInfoDto result = queryFactory
                .select(Projections.constructor(UserLuckInfoDto.class,
                        userLuckInfoEntity.koYearTop,
                        userLuckInfoEntity.chYearTop,
                        ExpressionUtils.as(
                                JPAExpressions.select(luckWordImgEntity.filePath.concat(luckWordImgEntity.fileName))
                                        .from(luckWordImgEntity)
                                        .where(luckWordImgEntity.koLuckWordName.eq(userLuckInfoEntity.koYearTop)
                                                .and(luckWordImgEntity.luckType.eq("1"))),
                                "yearTopImg"),
                        userLuckInfoEntity.koMonthTop,
                        userLuckInfoEntity.chMonthTop,
                        ExpressionUtils.as(
                                JPAExpressions.select(luckWordImgEntity.filePath.concat(luckWordImgEntity.fileName))
                                        .from(luckWordImgEntity)
                                        .where(luckWordImgEntity.koLuckWordName.eq(userLuckInfoEntity.koMonthTop)
                                                .and(luckWordImgEntity.luckType.eq("1"))),
                                "monthTopImg"),
                        userLuckInfoEntity.koDayTop,
                        userLuckInfoEntity.chDayTop,
                        ExpressionUtils.as(
                                JPAExpressions.select(luckWordImgEntity.filePath.concat(luckWordImgEntity.fileName))
                                        .from(luckWordImgEntity)
                                        .where(luckWordImgEntity.koLuckWordName.eq(userLuckInfoEntity.koDayTop)
                                                .and(luckWordImgEntity.luckType.eq("1"))),
                                "dayTopImg"),
                        userLuckInfoEntity.koTimeTop,
                        userLuckInfoEntity.chTimeTop,
                        ExpressionUtils.as(
                                JPAExpressions.select(luckWordImgEntity.filePath.concat(luckWordImgEntity.fileName))
                                        .from(luckWordImgEntity)
                                        .where(luckWordImgEntity.koLuckWordName.eq(userLuckInfoEntity.koTimeTop)
                                                .and(luckWordImgEntity.luckType.eq("1"))),
                                "timeTopImg"),
                        userLuckInfoEntity.koYearBtm,
                        userLuckInfoEntity.chYearBtm,
                        ExpressionUtils.as(
                                JPAExpressions.select(luckWordImgEntity.filePath.concat(luckWordImgEntity.fileName))
                                        .from(luckWordImgEntity)
                                        .where(luckWordImgEntity.koLuckWordName.eq(userLuckInfoEntity.koYearBtm)
                                                .and(luckWordImgEntity.luckType.eq("2"))),
                                "yearBtmImg"),
                        userLuckInfoEntity.koMonthBtm,
                        userLuckInfoEntity.chMonthBtm,
                        ExpressionUtils.as(
                                JPAExpressions.select(luckWordImgEntity.filePath.concat(luckWordImgEntity.fileName))
                                        .from(luckWordImgEntity)
                                        .where(luckWordImgEntity.koLuckWordName.eq(userLuckInfoEntity.koMonthBtm)
                                                .and(luckWordImgEntity.luckType.eq("2"))),
                                "monthBtmImg"),
                        userLuckInfoEntity.koDayBtm,
                        userLuckInfoEntity.chDayBtm,
                        ExpressionUtils.as(
                                JPAExpressions.select(luckWordImgEntity.filePath.concat(luckWordImgEntity.fileName))
                                        .from(luckWordImgEntity)
                                        .where(luckWordImgEntity.koLuckWordName.eq(userLuckInfoEntity.koDayBtm)
                                                .and(luckWordImgEntity.luckType.eq("2"))),
                                "dayBtmImg"),
                        userLuckInfoEntity.koTimeBtm,
                        userLuckInfoEntity.chTimeBtm,
                        ExpressionUtils.as(
                                JPAExpressions.select(luckWordImgEntity.filePath.concat(luckWordImgEntity.fileName))
                                        .from(luckWordImgEntity)
                                        .where(luckWordImgEntity.koLuckWordName.eq(userLuckInfoEntity.koTimeBtm)
                                                .and(luckWordImgEntity.luckType.eq("2"))),
                                "timeBtmImg")
                        ))
                .from(userLuckInfoEntity)
                .where(userLuckInfoEntity.userId.eq(userId))
                .fetchOne();

        return Optional.ofNullable(result);
    }

}