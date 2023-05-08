package com.example.luck_project.dto.request;

import com.example.luck_project.domain.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;

import javax.management.relation.Role;
import javax.persistence.Column;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

@Getter
@Setter
@ToString
@Valid
public class JoinReq {
    /**
     * 아이디(필수)
     */
    @NotBlank(message = "userId는 필수 입니다.")
    @Size(max = 20, message = "아이디는 20자리를 넘을 수 없습니다.")
    private String userId;

    /**
     * 비밀번호
     */
    @NotBlank(message = "password 필수 입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자리수여야 합니다. 영문 대소문자, 숫자, 특수문자를 1개 이상 포함해야 합니다.")
    private String password;

    /**
     * 이름
     */
    @NotBlank(message = "이름은 필수 입력값입니다.")
    @Pattern(regexp = "^[가-힣a-zA-Z]{2,20}$" , message = "이름은 특수문자를 포함하지 않은 2~20자리여야 합니다.")
    private String userName;

    /**
     * 닉네임
     */
    @NotBlank(message = "닉네임은 필수 입력값입니다.")
    @Pattern(regexp = "^[가-힣a-zA-Z0-9]{2,10}$" , message = "닉네임은 특수문자를 포함하지 않은 2~10자리여야 합니다.")
    private String nickname;

    /**
     * 생년월일
     */
    @NotBlank(message = "생년월일은 필수 입력값입니다.")
    @Pattern(regexp = "^[0-9]{8}$" , message = "생년월일은 8자리 숫자입니다.")
    private String birth;

    /**
     * 생일구분
     */
    @NotBlank(message = "생일구분은 필수 입력값입니다.")
    @Pattern(regexp = "^[0-9]{1}$" , message = "생일구분은 1자리 숫자 형식입니다.")
    private String birthFlag;

    /**
     * 태어난 시간
     */
    @Pattern(regexp = "^[0-9]{1}$" , message = "태어난 시간은 1자리 숫자 형식입니다.")
    private String birthTime;

    /**
     * 성별
     */
    @NotBlank(message = "성별은 필수 입력값입니다.")
    @Pattern(regexp = "^[MWmw]{1}$" , message = "성별은 1자리 M/W 형식입니다.")
    private String sex;

    /**
     * 핸드폰 번호
     */
    @NotBlank(message = "핸드폰번호는 필수 입력값입니다.")
    @Pattern(regexp = "^[0-9]{0,12}$" , message = "핸드폰번호는 최대 12자리 숫자 형식입니다.")
    private String phoneNm;

    /**
     * 로그인 구분
     */
    @NotBlank(message = "로그인 구분은 필수 입력값입니다.")
    @Pattern(regexp = "^[BKGbkg]{1}$" , message = "로그인 구분은 1자리 B,K,G 형식입니다.")
    private String loginDvsn;

    /**
     * 관심 카테고리 목록
     */
    private String cateCodeList;


    /* 암호화된 password */
    public void encryptPassword(String BCryptpassword) {
        this.password = BCryptpassword;
    }

    /* DTO -> Entity */
    public UserEntity toEntity() {
        LocalDateTime today = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String todayDate = today.format(formatter);

        UserEntity user = UserEntity.builder()
                .userId(userId)
                .userPw(password)
                .userName(userName)
                .nickName(nickname)
                .birth(birth)
                .birthTime(birthTime)
                .birthFlag(birthFlag)
                .sex(sex)
                .phoneNm(phoneNm)
                .cateCodeList(cateCodeList)
                .loginDvsn(loginDvsn)
                .passModDt(todayDate)
                .rgsttDtm(LocalDateTime.now())
                .roles(Collections.singletonList("USER"))
                .build();
        return user;
    }

}
