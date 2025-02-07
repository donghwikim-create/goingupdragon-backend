package com.goingupdragon.going_up_dragon.repository;

import com.goingupdragon.going_up_dragon.entity.SearchLogElasticsearch;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface SearchLogElasticsearchRepository extends ElasticsearchRepository<SearchLogElasticsearch, String> {
    List<SearchLogElasticsearch> findBySearchQueryStartingWith(String prefix);

    SearchLogElasticsearch findBySearchQuery(String searchQuery);

//    SearchLogElasticsearch findBySearchQuery(String query);
//
//    List<SearchLogElasticsearch> findBySearchQueryStartingWithOrderBySearchCountDesc(String query);
}
