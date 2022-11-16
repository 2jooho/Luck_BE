package com.example.luck_project.repository;

import com.example.luck_project.domain.UserEntity;
import com.example.luck_project.repository.querydsl.UserInfoRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserEntity, String>, UserInfoRepositoryCustom {
    /**
     * 회원 정보 조회
     * @param userId
     * @return
     */
    Optional<UserEntity> findByUserId(String userId);

    /**
     * 아이디 개수 조회
     * @param userId
     * @param loginDvsn
     * @return
     */
    Optional<Integer> countByUserIdAndLoginDvsn(String userId, String loginDvsn);

    /**
     * 닉네임 개수 조회
     * @param nickName
     * @return
     */
    Optional<Integer> countByNickName(String nickName);

    /**
     * 회원 개수 조회
     * @param userName
     * @param phoneNumber
     * @return
     */
    Optional<UserEntity> findByUserNameAndPhoneNm(String userName, String phoneNumber);

    /**
     * 아이디와 비밀번호를 통한 회원 정보 확인
     * @param userId
     * @param password
     * @return
     */
    boolean existsByUserIdAndUserPw(String userId, String password);

    /**
     * 회원 정보 조회
     * @param userId
     * @param loginDvsn
     * @return
     */
    Optional<UserEntity> findByUserIdAndLoginDvsn(String userId, String loginDvsn);


}
