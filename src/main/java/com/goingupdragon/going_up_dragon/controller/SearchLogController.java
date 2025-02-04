package com.goingupdragon.going_up_dragon.controller;

import com.goingupdragon.going_up_dragon.entity.SearchLog;
import com.goingupdragon.going_up_dragon.service.SearchLogService;
import lombok.RequiredArgsConstructor;
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

//import com.goingupdragon.going_up_dragon.entity.SearchLog;
//import com.goingupdragon.going_up_dragon.entity.UserInfo;
//import com.goingupdragon.going_up_dragon.service.SearchLogService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/search")
//@RequiredArgsConstructor
//public class SearchLogController {
//
//    private final SearchLogService searchLogService;
//
//    @PostMapping("/save")
//    public ResponseEntity<SearchLog> saveSearchQuery(@RequestParam String searchQuery,
//                                                     @AuthenticationPrincipal UserInfo userInfo) {
//        SearchLog savedLog = searchLogService.saveSearchQuery(searchQuery, userInfo);
//        return ResponseEntity.ok(savedLog);
//    }
//}


