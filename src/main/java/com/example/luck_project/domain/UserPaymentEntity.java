package com.example.luck_project.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "lck_user_payament_status")
@DynamicUpdate
public class UserPaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 아이디 */
    @Column(name = "USER_ID")
    private String userId;

    /** 사용 가능 개수 */
    @Column(name = "USE_CNT")
    private Integer useCnt;

    /** 사용 완료 개수 */
    @Column(name = "USE_CMPLN_CNT")
    private Integer useCmplnCnt;

    /** 상태 {P:패스, C:카운트}*/
    @Column(name = "STATUS")
    private String status;

    /** 등록일시 */
    @CreatedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "RGSTT_DTM")
    private LocalDateTime rgsttDtm;

    /** 등록자 */
    @Column(name = "RGPS_ID", length = 20)
    private String rgpsId = "API";

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "EDIT_DTM")
    protected LocalDateTime editDtm;

    @Column(name = "UPUS_ID")
    protected String upusId;

//    /** 결제 시작일 */
//    @Column(name = "PAYMENT_START")
//    private LocalDateTime paymentStart;
//
//    /** 결제 종료일 */
//    @Column(name = "PAYMENT_END")
//    private LocalDateTime paymentEnd;


    @Builder
    public void of(String userId, Integer useCnt, Integer useCmplnCnt, String status) {
        this.userId = userId;
        this.useCnt = useCnt;
        this.useCmplnCnt = useCmplnCnt;
        this.status = status;
    }

    /**
     * 사용 완료 개수 업데이트
     * @param useCmplnCnt
     */
    public void updateUseCmplnCnt(Integer useCmplnCnt){
        this.useCmplnCnt = useCmplnCnt;
    }

    /**
     * 사용 완료 개수 업데이트
     * @param useCmplnCnt
     */
    @Builder
    public void updateUserPayment(Integer useCmplnCnt, LocalDateTime editDtm, String upusId){
        this.useCmplnCnt = useCmplnCnt;
        this.editDtm = editDtm;
        this.upusId = upusId;
    }
}
