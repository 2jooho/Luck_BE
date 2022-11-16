package com.example.luck_project.service;

import com.example.luck_project.common.config.ApiSupport;
import com.example.luck_project.common.config.Oauth.Constant;
import com.example.luck_project.domain.UserEntity;
import com.example.luck_project.dto.request.JoinReq;
import com.example.luck_project.dto.request.SocialJoinReq;
import com.example.luck_project.dto.response.JoinRes;
import com.example.luck_project.exception.CustomException;
import com.example.luck_project.exception.ErrorCode;
import com.example.luck_project.exception.ParamCustomException;
import com.example.luck_project.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
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
        String userName = joinReq.getUserName();
        String nickName = joinReq.getNickname();

        logger.info("[{}][BASIC] 계정 존재 여부 체크", userName);
        //이름, 핸드폰번호 체크
        Optional<UserEntity> userEntity = userInfoRepository.findByUserNameAndPhoneNm(userName, joinReq.getPhoneNm());
        //사용자 정보 존재 시
        if(userEntity.isPresent()){
            logger.info("이미 존재하는 계정 : {}/{}", joinReq.getUserName(), joinReq.getPhoneNm());
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("userId", userEntity.get().getUserId());
            paramMap.put("loginDvsn", userEntity.get().getLoginDvsn());
            throw new ParamCustomException(ErrorCode.ALREADY_EXISTS_USER, paramMap);
        }

//        System.out.println("패스워드 테스트"+ seq);
//        logger.info("패테2: {}", seq);
//        logger.info("[{}] 비밀번호 암호화", nickName);
        /* 비밀번호 암호화 */
        joinReq.encryptPassword(passwordEncoder.encode(joinReq.getPassword()));
        System.out.printf("비밀번호 : {}", joinReq.getPassword());

        logger.info("[{}] 회원 정보 등록", nickName);
        UserEntity user = joinReq.toEntity();
        userInfoRepository.save(user);

        joinRes.setUserId(userId);
        joinRes.setNickName(nickName);

        return joinRes;
    }

    /**
     * 아이디 중복검사
     * @param userId
     * @param loginDvsn
     */
    public void idCheck(String userId, String loginDvsn){
        logger.info("[{}] 아이디 정보 개수 조회", userId);
        Optional<Integer> userCount = userInfoRepository.countByUserIdAndLoginDvsn(userId, loginDvsn);
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

    /**
     * 소셜 회원가입
     * @param socialJoinReq
     * @param socialLoginType
     * @return
     */
    @Transactional
    public JoinRes socialJoin(SocialJoinReq socialJoinReq, Constant.SocialLoginType socialLoginType){
        JoinRes joinRes = new JoinRes();
        String userId = socialJoinReq.getUserId().toUpperCase();
        String userName = socialJoinReq.getUserName();
        String nickName = socialJoinReq.getNickname();

        logger.info("[{}][BASIC] 계정 존재 여부 체크", userName);
        //이름, 핸드폰번호 체크
        Optional<UserEntity> UserEntity = userInfoRepository.findByUserNameAndPhoneNm(userName, socialJoinReq.getPhoneNm());
        //사용자 정보 존재 시
        if(UserEntity.isPresent()){
            logger.info("이미 존재하는 계정 : {}/{}", userName, socialJoinReq.getPhoneNm());
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("userId", UserEntity.get().getUserId());
            paramMap.put("loginDvsn", UserEntity.get().getLoginDvsn());
            throw new ParamCustomException(ErrorCode.ALREADY_EXISTS_USER, paramMap);
        }

        logger.info("[{}] 비밀번호 암호화", nickName);
        /* 비밀번호 암호화 */
        int idx = userId.indexOf("@");
        String password = userId.substring(0, idx);
        socialJoinReq.encryptPassword(passwordEncoder.encode(password + UserEntity.get().getUserNm()));
        System.out.printf("비밀번호 : {}"+ password+ "변형 : "+ password + UserEntity.get().getUserNm());

        logger.info("[{}] 회원 정보 등록", nickName);
        UserEntity user = socialJoinReq.toEntity();
        userInfoRepository.save(user);

        joinRes.setUserId(socialJoinReq.getUserId());
        joinRes.setNickName(nickName);

        return joinRes;
    }

}
