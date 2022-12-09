package com.example.luck_project.service;

import com.example.luck_project.common.config.ApiSupport;
import com.example.luck_project.common.config.Oauth.Constant;
import com.example.luck_project.common.util.DataCode;
import com.example.luck_project.domain.*;
import com.example.luck_project.dto.request.JoinReq;
import com.example.luck_project.dto.request.SocialJoinReq;
import com.example.luck_project.dto.response.JoinRes;
import com.example.luck_project.exception.CustomException;
import com.example.luck_project.exception.ErrorCode;
import com.example.luck_project.exception.ParamCustomException;
import com.example.luck_project.repository.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

import static com.example.luck_project.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class JoinService extends ApiSupport {

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
     * @param joinReq
     */
    @Transactional
    public JoinRes join(JoinReq joinReq){
        JoinRes joinRes = new JoinRes();
        String userId = joinReq.getUserId().toUpperCase();
        String userName = joinReq.getUserName();
        String nickName = joinReq.getNickname();
        String loginDvsn = joinReq.getLoginDvsn();

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

        this.userSetting(joinReq, userId, loginDvsn);

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
        String loginDvsn  = socialJoinReq.getLoginDvsn();

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

        logger.info("[{}] 회원 정보 등록", nickName);
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
     * @param userId
     * @param loginDvsn
     */
    @Transactional
    public void userSetting(JoinReq joinReq, String userId, String loginDvsn) {
        //사용자 정보 조회
//        Optional<UserEntity> userEntity = userInfoRepository.findByUserIdAndLoginDvsn(userId, loginDvsn);
//        userEntity.orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        logger.info("설정 시작");
        //사용자 결제 정보 조회
        Optional<UserPaymentEntity> userPaymentRepository = userPaymentInfoRepository.findByUserId(userId);
        if (userPaymentRepository.isPresent()) {
            logger.info("[{}] 사용자 설정 정보 존재", userId);
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
        if(StringUtils.equals(joinReq.getBirthFlag(),"1")){
            calendaRepository = luckCalendaRepository.findByCdSyAndCdSmAndCdSd(year,month,day);
        }else{
            calendaRepository = luckCalendaRepository.findByCdLyAndCdLmAndCdLd(year,month,day);
        }
        calendaRepository.orElseThrow(() -> new CustomException(USER_LUCK_NOT_FOUND));
        System.out.println("년지:"+calendaRepository.get().getCdKyganjee()+"일지:"+calendaRepository.get().getCdKdganjee());
        String yearB = calendaRepository.get().getCdKyganjee().substring(1,2);
        String yearT = calendaRepository.get().getCdKyganjee().substring(0,1);
        String dayB = calendaRepository.get().getCdKdganjee().substring(1,2);
        String dayT = calendaRepository.get().getCdKdganjee().substring(0,1);

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
        Integer pureCombCnt = userPureCombRepository.countByLuckCnctnIn(luckCnctnList);
        if(!(pureCombCnt >= 12)){
            System.out.println("arrlength:" + DataCode.VERS_YEAR_NAME_ARR.length);
            int pureYearCnt = DataCode.getCodeNum(DataCode.VERS_YEAR_NAME_ARR, yearB);
            int pureDayCnt = DataCode.getCodeNum(DataCode.VERS_YEAR_NAME_ARR, dayB);
            System.out.println("pureYearCnt : " + pureYearCnt + "pureDayCnt : " + pureDayCnt);
            for(int i = 0; i < DataCode.VERS_YEAR_NAME_ARR.length; i++){
                logger.info("befor pureYearCnt :{}/pureDayCnt:{}", pureYearCnt, pureDayCnt);
                if(pureYearCnt <= 0){
                    pureYearCnt = pureYearCnt+12;
                }else if(pureDayCnt <= 0){
                    pureDayCnt = pureDayCnt+12;
                }
                logger.info("after pureYearCnt :{}/pureDayCnt:{}", pureYearCnt, pureDayCnt);

                pureYearCnt = pureYearCnt-1;
                pureDayCnt = pureDayCnt-1;

                UserPureCombinationEntity userPureCombinationEntity = new UserPureCombinationEntity();
                UserPureCombinationEntity chnUserPureCombinationEntity = new UserPureCombinationEntity();
                String pureYear = DataCode.PURE_LUCK_NAME_ARR[pureYearCnt];
                String pureDay = DataCode.PURE_LUCK_NAME_ARR[pureDayCnt];
                logger.info("pureYear:{}/pureDay:{}", pureYear, pureDay);
                //비장술 년/일 정방향 등록
                userPureCombinationEntity.of(luckCnctn, DataCode.VERS_YEAR_NAME_ARR[i] ,pureYear, pureDay);
                userPureCombRepository.save(userPureCombinationEntity);
                //비장술 일/년 뒤집어서 등록
                chnUserPureCombinationEntity.of(luckCnctn, DataCode.VERS_YEAR_NAME_ARR[i] ,pureDay, pureYear);
                userPureCombRepository.save(chnUserPureCombinationEntity);

            }
        }

    }

    /**
     * 회원탈퇴
     * @param userId
     * @param loginDvsn
     */
    @Transactional
    public void userBolter(String userId, String loginDvsn) {
        logger.info("[{}][{}] 회원탈퇴 처리", userId, loginDvsn);
        //사용자 정보 조회
        Optional<UserEntity> userEntity = userInfoRepository.findByUserIdAndLoginDvsn(userId, loginDvsn);
        userEntity.orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        userEntity.get().bolterDateUpdate(LocalDateTime.now(),LocalDateTime.now(), "API");
        //회원 탈퇴 처리
        userInfoRepository.delete(userEntity.get());
    }

}
