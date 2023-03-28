package com.example.luck_project.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OtpRes {
    /** otp키 */
    private String otpSsnKey;

    /** OTP만료일시 */
    private String otpXprtnDtm;

    /** OTP유효시간(분) */
    private String otpEfctvMin;

    /** OTP발송 휴대폰 번호 */
    private String otpRcptnCpno;

    /** 당일 인증제한기준횟수 */
    private String otpFlrrstrcTmcnt;

}
