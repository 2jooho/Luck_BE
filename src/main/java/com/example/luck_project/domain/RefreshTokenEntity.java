package com.example.luck_project.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenEntity {

    /** 리프레시 토큰 */
    @Id
    @Column(nullable = false)
    private String refreshToken;


}
