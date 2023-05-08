package com.example.luck_project.service;

import com.example.luck_project.common.config.Oauth.Constant;
import com.example.luck_project.common.util.DataCode;
import com.example.luck_project.constants.ResponseCode;
import com.example.luck_project.domain.*;
import com.example.luck_project.dto.request.FindIdReq;
import com.example.luck_project.dto.request.JoinReq;
import com.example.luck_project.dto.request.ResetPwReq;
import com.example.luck_project.dto.request.SocialJoinReq;
import com.example.luck_project.dto.response.JoinRes;
import com.example.luck_project.dto.response.ResetPwUserInfoRes;
import com.example.luck_project.exception.CustomException;
import com.example.luck_project.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

import static com.example.luck_project.constants.ResponseCode.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class JoinService {

    @Autowired
    UserPaymentInfoRepository userPaymentInfoRepository;

    @Autowired
    UserPureCombRepository userPureCombRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private LuckCalendaRepository luckCalendaRepository;

    @Autowired
    private UserLuckInfoRepository userLuckInfoRepository;

    private final PasswordEncoder passwordEncoder;


    /**
     * 회원가입
     *
     * @param joinReq
     */
    @Transactional
    public JoinRes join(JoinReq joinReq) {
        JoinRes joinRes = new JoinRes();
        String userId = joinReq.getUserId().toUpperCase();
        String userName = joinReq.getUserName();
        String nickName = joinReq.getNickname();
        String loginDvsn = joinReq.getLoginDvsn();

        log.info("[{}][BASIC] 계정 존재 여부 체크", userName);
        //이름, 핸드폰번호 체크
        Optional<UserEntity> userEntity = userInfoRepository.findByUserNameAndPhoneNm(userName, joinReq.getPhoneNm());
        //사용자 정보 존재 시
        if (userEntity.isPresent()) {
            log.info("이미 존재하는 계정 : {}/{}", userId, joinReq.getUserName());
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("userId", userEntity.get().getUserId());
            paramMap.put("loginDvsn", userEntity.get().getLoginDvsn());
            throw new CustomException(ALREADY_EXISTS_USER);
        }

//        System.out.println("패스워드 테스트"+ seq);
//        log.info("패테2: {}", seq);
//        log.info("[{}] 비밀번호 암호화", nickName);
        /* 비밀번호 암호화 */
        joinReq.encryptPassword(passwordEncoder.encode(joinReq.getPassword()));

        log.info("[{}] 회원 정보 등록", nickName);
        UserEntity user = joinReq.toEntity();
        userInfoRepository.save(user);

        joinRes.setUserId(userId);
        joinRes.setNickName(nickName);

        this.userSetting(joinReq, userId, loginDvsn);

        return joinRes;
    }

    /**
     * 아이디 중복검사
     *
     * @param userId
     * @param loginDvsn
     */
    public void idCheck(String userId, String loginDvsn) {
        log.info("[{}] 아이디 정보 개수 조회", userId);
        Optional<Integer> userCount = userInfoRepository.countByUserIdAndLoginDvsn(userId, loginDvsn);
        //아이디 개수 존재 시
        if (userCount.isPresent() && userCount.get() > 0) {
            log.info("이미 존재하는 아이디 : {}", userId);
            throw new CustomException(ResponseCode.ALREADY_EXISTS_USER);
        }
    }

    /**
     * 닉네임 중복검사
     *
     * @param nickName
     */
    public void nickNameCheck(String nickName) {
        log.info("[{}] 닉네임 정보 개수 조회", nickName);
        Optional<Integer> nickNameCount = userInfoRepository.countByNickName(nickName);
        //닉네임 개수 존재 시
        if (nickNameCount.isPresent() && nickNameCount.get() > 0) {
            log.info("이미 존재하는 닉네임 : {}", nickName);
            throw new CustomException(ResponseCode.ALREADY_EXISTS_NICKNAME);
        }
    }

    /**
     * 소셜 회원가입
     *
     * @param socialJoinReq
     * @param socialLoginType
     * @return
     */
    @Transactional
    public JoinRes socialJoin(SocialJoinReq socialJoinReq, Constant.SocialLoginType socialLoginType) {
        JoinRes joinRes = new JoinRes();
        String userId = socialJoinReq.getUserId().toUpperCase();
        String userName = socialJoinReq.getUserName();
        String nickName = socialJoinReq.getNickname();
        String loginDvsn = socialJoinReq.getLoginDvsn();

        log.info("[{}][BASIC] 계정 존재 여부 체크", userName);
        //이름, 핸드폰번호 체크
        Optional<UserEntity> UserEntity = userInfoRepository.findByUserNameAndPhoneNm(userName, socialJoinReq.getPhoneNm());
        //사용자 정보 존재 시
        if (UserEntity.isPresent()) {
            log.info("이미 존재하는 계정 : {}/{}/{}", userId, userName, socialJoinReq.getPhoneNm());
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("userId", UserEntity.get().getUserId());
            paramMap.put("loginDvsn", UserEntity.get().getLoginDvsn());
            throw new CustomException(ALREADY_EXISTS_USER);
        }

        log.info("[{}] 회원 정보 등록", nickName);
        UserEntity user = socialJoinReq.toEntity();
        userInfoRepository.save(user);

        joinRes.setUserId(socialJoinReq.getUserId());
        joinRes.setNickName(nickName);


        JoinReq joinReq = new JoinReq();
        joinReq.setBirth(socialJoinReq.getBirth());
        joinReq.setBirthFlag(socialJoinReq.getBirthFlag());

        this.userSetting(joinReq, userId, loginDvsn);

        return joinRes;
    }

    /**
     * 유저 초기설정
     *
     * @param userId
     * @param loginDvsn
     */
    @Transactional
    public void userSetting(JoinReq joinReq, String userId, String loginDvsn) {
        //사용자 정보 조회
//        Optional<UserEntity> userEntity = userInfoRepository.findByUserIdAndLoginDvsn(userId, loginDvsn);
//        userEntity.orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        log.info("설정 시작");
        //사용자 결제 정보 조회
        Optional<UserPaymentEntity> userPaymentRepository = userPaymentInfoRepository.findByUserId(userId);
        if (userPaymentRepository.isPresent()) {
            log.info("[{}] 사용자 설정 정보 존재", userId);
            throw new CustomException(USER_INFO_FOUND);
        }
        UserPaymentEntity userPaymentEntity = new UserPaymentEntity();
        userPaymentEntity.of(userId, 5, 0, "C");
        userPaymentInfoRepository.save(userPaymentEntity);

        String birth = joinReq.getBirth();
        String year = birth.substring(0, 4);
        String month = birth.substring(5, 6);
        String day = birth.substring(6);
        System.out.println("year:" + year + "month" + month + "day" + day);

        Optional<LuckCalendaEntity> calendaRepository;
        if (StringUtils.equals(joinReq.getBirthFlag(), "1")) {
            calendaRepository = luckCalendaRepository.findByCdSyAndCdSmAndCdSd(year, month, day);
        } else {
            calendaRepository = luckCalendaRepository.findByCdLyAndCdLmAndCdLd(year, month, day);
        }
        calendaRepository.orElseThrow(() -> new CustomException(USER_LUCK_NOT_FOUND));
        System.out.println("년지:" + calendaRepository.get().getCdKyganjee() + "일지:" + calendaRepository.get().getCdKdganjee());
        String yearB = calendaRepository.get().getCdKyganjee().substring(1, 2);
        String yearT = calendaRepository.get().getCdKyganjee().substring(0, 1);
        String dayB = calendaRepository.get().getCdKdganjee().substring(1, 2);
        String dayT = calendaRepository.get().getCdKdganjee().substring(0, 1);

        //사용자 사주 정보 등록
        UserLuckInfoEntity userLuckInfoEntity = new UserLuckInfoEntity();
        userLuckInfoEntity.of(userId, yearT, yearB, dayT, dayB);
        userLuckInfoRepository.save(userLuckInfoEntity);

        //사용자의 12가지 비장술 조합을 확인 후 갱신
        String luckCnctn = dayB.concat(yearB);
        String chnLuckCnctn = yearB.concat(dayB);
        List<String> luckCnctnList = new ArrayList<>();
        luckCnctnList.add(luckCnctn);
        luckCnctnList.add(chnLuckCnctn);
        log.info("비장술 조합 확인");
        Integer pureCombCnt = userPureCombRepository.countByLuckCnctnIn(luckCnctnList);
        log.info("비장술 조합 조회:{}", pureCombCnt);
        if (!(pureCombCnt >= 12)) {
            System.out.println("arrlength:" + DataCode.VERS_YEAR_NAME_ARR.length);
            int pureYearCnt = DataCode.getCodeNum(DataCode.VERS_YEAR_NAME_ARR, yearB);
            int pureDayCnt = DataCode.getCodeNum(DataCode.VERS_YEAR_NAME_ARR, dayB);
            System.out.println("pureYearCnt : " + pureYearCnt + "pureDayCnt : " + pureDayCnt);
            for (int i = 0; i < DataCode.VERS_YEAR_NAME_ARR.length; i++) {
                log.info("befor pureYearCnt :{}/pureDayCnt:{}", pureYearCnt, pureDayCnt);
                if (pureYearCnt <= 0) {
                    pureYearCnt = pureYearCnt + 12;
                } else if (pureDayCnt <= 0) {
                    pureDayCnt = pureDayCnt + 12;
                }
                log.info("after pureYearCnt :{}/pureDayCnt:{}", pureYearCnt, pureDayCnt);

                pureYearCnt = pureYearCnt - 1;
                pureDayCnt = pureDayCnt - 1;

                UserPureCombinationEntity userPureCombinationEntity = new UserPureCombinationEntity();
                UserPureCombinationEntity chnUserPureCombinationEntity = new UserPureCombinationEntity();
                String pureYear = DataCode.PURE_LUCK_NAME_ARR[pureYearCnt];
                String pureDay = DataCode.PURE_LUCK_NAME_ARR[pureDayCnt];
                log.info("pureYear:{}/pureDay:{}", pureYear, pureDay);
                //비장술 년/일 정방향 등록
                userPureCombinationEntity.of(luckCnctn, DataCode.VERS_YEAR_NAME_ARR[i], pureYear, pureDay);
                userPureCombRepository.save(userPureCombinationEntity);
                //비장술 일/년 뒤집어서 등록
                chnUserPureCombinationEntity.of(luckCnctn, DataCode.VERS_YEAR_NAME_ARR[i], pureDay, pureYear);
                userPureCombRepository.save(chnUserPureCombinationEntity);

            }
        }

    }

    /**
     * 회원탈퇴
     *
     * @param userId
     * @param loginDvsn
     */
    @Transactional
    public void userBolter(String userId, String loginDvsn) {
        log.info("[{}][{}] 회원탈퇴 처리", userId, loginDvsn);
        //사용자 정보 조회
        Optional<UserEntity> userEntity = userInfoRepository.findByUserIdAndLoginDvsn(userId, loginDvsn);
        userEntity.orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        userEntity.get().bolterDateUpdate(LocalDateTime.now(), LocalDateTime.now(), "API");
        //회원 탈퇴 처리
        userInfoRepository.delete(userEntity.get());
    }

    /**
     * 사용자 ID 찾기
     * @param findIdReq
     * @return
     */
    public String findUserId(FindIdReq findIdReq) {
        Optional<UserEntity> userEntity = userInfoRepository.findByUserNameAndPhoneNmAndBirth(findIdReq.getName(), findIdReq.getPhoneNm(), findIdReq.getBrith());
        userEntity.orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        String userId = userEntity.get().getUserId();
        return userId;
    }

    /**
     * 비밀번호 재설정(1) - 아이디로 회원정보 조회
     * @param userId
     * @return
     */
    public ResetPwUserInfoRes getResetPwUserInfo(String userId) {
        //회원정보 조회
        Optional<UserEntity> userEntity = userInfoRepository.findByUserId(userId);
        userEntity.orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        //응답설정
        ResetPwUserInfoRes res = new ResetPwUserInfoRes();
        res.entityToResetPwUserInfoRes(userEntity.get());

        return res;
    }

    /**
     * 비밀번호 재설정(2) - 재설정
     * @return
     */
    @Transactional
    public void setResetPw(ResetPwReq resetPwReq) {
        if(resetPwReq.getNewPassword().equals(resetPwReq.getNewPasswordCheck())){
            throw new CustomException(RESET_PASSWORD_MATCH_FAIL);
        }

        //회원정보 조회
        Optional<UserEntity> userEntity = userInfoRepository.findByUserId(resetPwReq.getUserId());
        userEntity.orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        //비밀번호 업데이트
        userEntity.get().resetPassword(passwordEncoder.encode(resetPwReq.getNewPassword()));
    }

}
