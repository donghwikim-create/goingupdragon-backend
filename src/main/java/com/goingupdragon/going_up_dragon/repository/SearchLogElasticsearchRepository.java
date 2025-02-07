package com.goingupdragon.going_up_dragon.repository;

import com.goingupdragon.going_up_dragon.entity.SearchLogElasticsearch;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface SearchLogElasticsearchRepository extends ElasticsearchRepository<SearchLogElasticsearch, String> {
}
