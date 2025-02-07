package com.goingupdragon.going_up_dragon.controller;

import com.goingupdragon.going_up_dragon.entity.SearchLog;
import com.goingupdragon.going_up_dragon.service.SearchLogService;
import com.goingupdragon.going_up_dragon.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.client.RequestOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchLogController {

    private final SearchLogService searchLogService;

    @PostMapping("/save")
    public ResponseEntity<SearchLog> saveSearchQuery(@RequestParam String searchQuery) {
        SearchLog savedLog = searchLogService.saveSearchQuery(searchQuery);
        return ResponseEntity.ok(savedLog);
    }
}


