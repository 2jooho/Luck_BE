package com.example.luck_project.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name="lck_luck_word_img", indexes = @Index(name = "IDX_LCK_LUCK_WORD_IMG_01", columnList = "ko_luck_word_name, luck_type"))
public class LuckWordImgEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 한글 사주 단어명 */
    @Column(name = "ko_luck_word_name", nullable = false, length = 10)
    private String koLuckWordName;

    /** 운세 타입(1:천간, 2:지지) */
    @Column(name = "luck_type", nullable = false, length = 1)
    private String luckType;

    /** 한자 사주 단어명 */
    @Column(name = "ch_luck_word_name", nullable = true, length = 10)
    private String chLuckWordName;

    /** 이미지 파일경로 */
    @Column(name = "file_path", nullable = false, length = 150)
    private String filePath;

    /** 이미지 파일명 */
    @Column(name = "file_name", nullable = false, length = 100)
    private String fileName;
}
