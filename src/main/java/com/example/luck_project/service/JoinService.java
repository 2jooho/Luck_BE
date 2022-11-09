package com.example.luck_project.service;

import com.example.luck_project.common.config.ApiSupport;
import com.example.luck_project.domain.UserEntity;
import com.example.luck_project.dto.request.JoinReq;
import com.example.luck_project.dto.response.JoinRes;
import com.example.luck_project.exception.CustomException;
import com.example.luck_project.exception.ErrorCode;
import com.example.luck_project.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JoinService extends ApiSupport {

    @Autowired
    private UserInfoRepository userInfoRepository;
    private final PasswordEncoder passwordEncoder;


    /**
     * 회원가입
     * @param joinReq
     */
    @Transactional
    public JoinRes join(JoinReq joinReq){
        JoinRes joinRes = new JoinRes();
        String userId = joinReq.getUserId().toUpperCase();
        String nickName = joinReq.getNickname();

        logger.info("[{}] 회원가입 진행", userId);
        Optional<Integer> userCount = userInfoRepository.countByUserId(userId);
        //아이디 개수 존재 시
        if(userCount.isPresent() && userCount.get() > 0){
            logger.info("이미 존재하는 아이디 : {}", userId);
            throw new CustomException(ErrorCode.ALREADY_EXISTS_USER);
        }

        logger.info("[{}] 닉네임 정보 개수 조회", nickName);
        Optional<Integer> nickNameCount = userInfoRepository.countByNickName(nickName);
        //닉네임 개수 존재 시
        if(nickNameCount.isPresent() && nickNameCount.get() > 0){
            logger.info("이미 존재하는 닉네임 : {}", nickName);
            throw new CustomException(ErrorCode.ALREADY_EXISTS_NICKNAME);
        }

        //이름, 핸드폰번호 체크
        Optional<Integer> userInfoCount = userInfoRepository.countByUserNameAndPhoneNm(joinReq.getUserName(), joinReq.getPhoneNm());
        //사용자 정보 존재 시
        if(userInfoCount.isPresent() && userInfoCount.get() > 0){
            logger.info("이미 존재하는 계정 : {}/{}", joinReq.getUserName(), joinReq.getPhoneNm());
            throw new CustomException(ErrorCode.ALREADY_EXISTS_USER);
        }

        logger.info("[{}] 회원가입", nickName);
        /* 비밀번호 암호화 */
        joinReq.encryptPassword(passwordEncoder.encode(joinReq.getPassword()));
        System.out.printf("비밀번호 : {}", joinReq.getPassword());

        UserEntity user = joinReq.toEntity();
        userInfoRepository.save(user);

        joinRes.setUserId(userId);
        joinRes.setNickName(nickName);

        return joinRes;
    }

    /**
     * 아이디 중복검사
     * @param userId
     */
    public void idCheck(String userId){
        logger.info("[{}] 아이디 정보 개수 조회", userId);
        Optional<Integer> userCount = userInfoRepository.countByUserId(userId);
        //아이디 개수 존재 시
        if(userCount.isPresent() && userCount.get() > 0){
            logger.info("이미 존재하는 아이디 : {}", userId);
            throw new CustomException(ErrorCode.ALREADY_EXISTS_USER);
        }
    }

    /**
     * 닉네임 중복검사
     * @param nickName
     */
    public void nickNameCheck(String nickName){
        logger.info("[{}] 닉네임 정보 개수 조회", nickName);
        Optional<Integer> nickNameCount = userInfoRepository.countByNickName(nickName);
        //닉네임 개수 존재 시
        if(nickNameCount.isPresent() && nickNameCount.get() > 0){
            logger.info("이미 존재하는 닉네임 : {}", nickName);
            throw new CustomException(ErrorCode.ALREADY_EXISTS_NICKNAME);
        }
    }

}
