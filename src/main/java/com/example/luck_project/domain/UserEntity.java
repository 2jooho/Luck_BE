package com.example.luck_project.domain;

import com.example.luck_project.dto.response.ResetPwUserInfoRes;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "lck_user_info")
@DynamicUpdate
@Builder
//@SequenceGenerator(
//        name = "SEQ_USER_INFO"
//        , sequenceName = "USER_INFO_SEQ"
//        , initialValue = 1
//        , allocationSize = 1
//)
@SQLDelete(sql = "UPDATE lck_user_info SET bolter_falg = true WHERE user_no = ?")
@Where(clause = "bolter_falg = false")
public class UserEntity implements UserDetails, Persistable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_NO")
    private Long id;

    /** 아이디 */
    @Column(name = "USER_ID", nullable = false, unique = true)
    private String userId;

//    /** 사용자 번호 */
//    @GeneratedValue(
//            strategy = GenerationType.SEQUENCE
//            , generator = "SEQ_USER_INFO"
//    )
//    @Column(name = "USER_NM")
//    private long userNm;

    /** 패스워드 */
    @Column(name = "USER_PW")
    private String userPw;

    /** 이름 */
    @Column(name = "USER_NAME")
    private String userName;

    /** 닉네임 */
    @Column(name = "NICK_NAME")
    private String nickName;

    /** 생년월일 */
    @Column(name = "BIRTH")
    private String birth;

    /** 생일구분 */
    @Column(name = "BIRTH_FLAG")
    private String birthFlag;

    /** 태어난 시간 구분 */
    @Column(name = "BIRTH_TIME_TYPE", length = 2)
    private String birthTimeType;

    /** 성별 */
    @Column(name = "SEX")
    private String sex;

    /** 핸드폰 번호 */
    @Column(name = "PHONE_NM")
    private String phoneNm;

    /** 관심 카테고리 코드 리스트 (구분자 ',' 나열) */
    @Column(name = "CATE_CODE_LIST")
    private String cateCodeList;

    /** 마지막 로그인 일시 */
    @CreatedDate
    @Column(name = "LAST_LOGIN_DATE", length = 14)
    private String lastLoginDt;

    /** 로그인 구분 */
    @Column(name = "LOGIN_DVSN_CODE", length = 1)
    private String loginDvsn;

    /** 휴면계정여부 */
    @Column(name = "INACTIVITY_FLAG", length = 1)
    private String inactivityFlag;

    /** 비밀번호 변경일 */
    @Column(name = "PASS_MOD_DT", length = 14)
    private String passModDt;

    /** 회원 탈퇴 여부 */
    //delete 쿼리가 발생하였을때, sqldelete어노테이션의 쿼리가 실행된다.
    //sqldelete의 실행 쿼리는 트랜잭션이 끝나고 실제 db에 쿼리를 보낼때 관리
    @Column(name = "BOLTER_FALG")
    @Builder.Default
    private boolean deleted = Boolean.FALSE; // 탈퇴 여부 기본값 false

    /** 회원 탈퇴 일자 */
    @Column(name = "BOLTER_DATE")
    private LocalDateTime bolterDate;

    /** 등록일시 */
    @CreatedDate
    @Column(name = "RGSTT_DTM")
    private LocalDateTime rgsttDtm;

    /** 등록자 */
    @Column(name = "RGPS_ID")
    @Builder.Default
    private String rgpsId = "API";

    /** 수정일시 */
    @CreatedDate
    @Column(name = "EDIT_DTM")
    private LocalDateTime editDtm;

    /** 수정자 */
    @Column(name = "UPUS_ID", length = 20)
    private String upusId;

//    @JoinColumn(name = "USER_NO")
//    @OneToOne(fetch = FetchType.LAZY)
//    private UserLuckInfoEntity userLuckInfoEntity;

    public void lastLoginDtUpdate(String lastLoginDt, LocalDateTime editDtm, String upusId) {
        this.lastLoginDt = lastLoginDt;
        this.editDtm = editDtm;
        this.upusId = upusId;
    }
    public void bolterDateUpdate(LocalDateTime bolterDate, LocalDateTime editDtm, String upusId) {
        this.bolterDate = bolterDate;
        this.editDtm = editDtm;
        this.upusId = upusId;
    }


    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return rgsttDtm == null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return userPw;
    }

    @Override
    public String getUsername() {
        return userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @OneToOne(mappedBy = "userEntity")
    UserMobileDeviceEntity userMobileDeviceEntity;

    /**
     * 비밀번호 재설정
     * @param password
     */
    public void resetPassword(String password){
        this.userPw = password;
    }

}


