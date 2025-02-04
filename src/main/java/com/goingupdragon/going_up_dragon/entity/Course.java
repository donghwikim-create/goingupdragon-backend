package com.goingupdragon.going_up_dragon.entity;

import com.goingupdragon.going_up_dragon.enums.Enums;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "courses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer courseId;

    @Column(nullable = false, length = 255)
    private String courseTitle;

    @ManyToOne
    @JoinColumn(name = "info_id", nullable = false)
    private UserInfo instructor; // 강사 정보 (외래키)

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private Integer mainCategoryId;

    @Column(nullable = false)
    private Integer subCategoryId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Enums.CourseLevel level;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Enums.CourseLanguage language;

    @Column(nullable = false)
    private Integer duration;

    private Integer price;

    private Integer subjectTag1;
    private Integer subjectTag2;
    private Integer subjectTag3;

    @Column(nullable = false, length = 255)
    private String courseThumbnail;

    @Column(nullable = false, length = 255)
    private String shortDescription;
}
