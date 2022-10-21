package com.example.luck_project.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "lck_user_payament_status")
@DynamicUpdate
public class UserPaymentEntity {

    /** 아이디 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private String userId;

    /** 사용 가능 개수 */
    @Column(name = "USE_CNT")
    private Integer useCnt;

    /** 사용 완료 개수 */
    @Column(name = "USE_CMPLN_CNT")
    private Integer useCmplnCnt;

    /** 결제 상태 */
    @Column(name = "STATUS")
    private String status;

    /** 결제 시작일 */
    @Column(name = "PAYMENT_START")
    private LocalDateTime paymentStart;

    /** 결제 종료일 */
    @Column(name = "PAYMENT_END")
    private LocalDateTime paymentEnd;

    /**
     * 사용 완료 개수 업데이트
     * @param useCmplnCnt
     */
    public void updateUseCmplnCnt(Integer useCmplnCnt){
        this.useCmplnCnt = useCmplnCnt;
    }

}
