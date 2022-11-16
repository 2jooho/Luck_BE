package com.example.luck_project.domain;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity(name = "lck_user_mobile_device")
@Builder
@DynamicUpdate
public class UserMobileDeviceEntity {

    /** 로그인 히스토리 시퀀스 */
    @Id
    @Column(name = "USER_ID", length = 20)
    private String userId;

    /** 디바이스 타입(1:ios, 2:안드로이드, 9:기타) */
    @Column(name = "DEVICE_TYPE", length = 1)
    private String deviceType;

    /** 푸시 아이디 */
    @Column(name = "PUSH_ID", length = 500)
    private String pushId;

    /** 디바이스 아이디 */
    @Column(name = "DEVICE_ID", length = 100)
    private String deviceId;

    /** 등록일시 */
    @CreatedDate
    @Column(name = "RGSTT_DTM")
    private LocalDateTime rgsttDtm;

    /** 등록자 */
    @Column(name = "RGPS_ID", length = 20)
    private String rgpsId = "API";

    /** 수정일시 */
    @CreatedDate
    @Column(name = "EDIT_DTM")
    private LocalDateTime editDtm;

    /** 수정자 */
    @Column(name = "UPUS_ID", length = 20)
    private String upusId;
}
