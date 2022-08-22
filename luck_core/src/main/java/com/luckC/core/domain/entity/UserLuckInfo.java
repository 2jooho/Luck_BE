package com.luckC.core.domain.entity;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_luck_info")
public class UserLuckInfo{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private String userId;

    @OneToOne(mappedBy = "userLuckInfo")
    private UserInfo userInfo;

}