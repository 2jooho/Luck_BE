package com.example.luck_project.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name="lck_user_recommand_star")
public class UserRecommandStarEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 추천 등록자 */
    @Column(name = "recommand_rgps_userid", nullable = false, length = 20)
    private String recommandRgpsUserId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recommand_code")
    private UserEntity userEntity;

    @Builder
    public void of(String recommandRgpsUserId, UserEntity userEntity) {
        this.recommandRgpsUserId = recommandRgpsUserId;
        this.userEntity = userEntity;
    }
}
