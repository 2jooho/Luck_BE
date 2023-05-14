package com.example.luck_project.dto.request;

import com.example.luck_project.domain.UserEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

@Getter
@Setter
@ToString
@Valid
public class ResetPwReq {
    /**
     * 아이디(필수)
     */
    @NotBlank(message = "userId는 필수 입니다.")
    @Size(max = 20, message = "아이디는 20자리를 넘을 수 없습니다.")
    private String userId;

    /**
     * 비밀번호
     */
    @NotBlank(message = "newPassword 필수 입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자리수여야 합니다. 영문 대소문자, 숫자, 특수문자를 1개 이상 포함해야 합니다.")
    private String newPassword;

    /**
     * 비밀번호 확인
     */
    @NotBlank(message = "newPasswordCheck 필수 입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자리수여야 합니다. 영문 대소문자, 숫자, 특수문자를 1개 이상 포함해야 합니다.")
    private String newPasswordCheck;
}
