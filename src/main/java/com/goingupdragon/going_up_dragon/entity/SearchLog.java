package com.goingupdragon.going_up_dragon.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "search_log")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // auto increment 설정
    @Column(name = "search_log_id")
    private Long searchLogId;

    @Column(name = "info_id")
    private Integer infoId;

    @Column(name = "search_query", length = 255)
    private String searchQuery;

    @Column(name = "search_category", length = 255)
    private String searchCategory;

    @Column(name = "search_time")
    private LocalDateTime searchTime;
}
