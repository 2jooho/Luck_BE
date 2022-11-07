package com.example.luck_project.domain;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "lck_user_info")
@Builder
public class UserEntity implements UserDetails, Persistable<String> {

    /** 아이디 */
    @Id
    @Column(name = "USER_ID")
    private String userId;

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

    /** 태어난 시간 */
    @Column(name = "BIRTH_TIME")
    private String birthTime;

    /** 성별 */
    @Column(name = "SEX")
    private String sex;

    /** 관심 카테고리 코드 리스트 (구분자 ',' 나열) */
    @Column(name = "CATE_CODE_LIST")
    private String cateCodeList;

    /** 등록일시 */
    @CreatedDate
    @Column(name = "RGSTT_DTM")
    private LocalDateTime rgsttDtm;

    /** 등록자 */
    @Column(name = "RGPS_ID")
    private String rgpsId = "API";

//    @JoinColumn(name = "USER_ID")
//    @OneToOne(fetch = FetchType.LAZY)
//    private UserLuckInfoEntity userLuckInfoEntity;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Override
    public String getId() {
        return userId;
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

}


