package com.goingupdragon.going_up_dragon.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.LocalDateTime;

@Document(indexName = "search_logs") // 인덱스 이름
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchLogElasticsearch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto Increment
    private String id;
    private String searchQuery;
    private String searchCategory;
    private LocalDateTime searchTime;
}
