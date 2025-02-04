package com.goingupdragon.going_up_dragon.service;

import com.goingupdragon.going_up_dragon.entity.SearchLog;
import com.goingupdragon.going_up_dragon.entity.UserInfo;
import com.goingupdragon.going_up_dragon.repository.SearchLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SearchLogService {

    private final SearchLogRepository searchLogRepository;
    private final UserInfoService userInfoService;

    @Transactional
    public SearchLog saveSearchQuery(String searchQuery) {
        // 로그인한 사용자 정보 가져오기 (없으면 null)
        UserInfo userInfo = userInfoService.getLoggedInUser().orElse(null);

        SearchLog searchLog = SearchLog.builder()
                .userInfo(userInfo)  // 로그인한 사용자 정보 (없으면 null)
                .searchQuery(searchQuery)
                .searchCategory("")
                .searchTime(LocalDateTime.now())
                .build();

        searchLog = searchLogRepository.save(searchLog);

        // search_category 업데이트 실행
        searchLogRepository.updateSearchCategory(searchLog.getSearchLogId());

        return searchLog;
    }
}

//import com.goingupdragon.going_up_dragon.entity.SearchLog;
//import com.goingupdragon.going_up_dragon.entity.UserInfo;
//import com.goingupdragon.going_up_dragon.repository.SearchLogRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import java.time.LocalDateTime;
//
//@Service
//@RequiredArgsConstructor
//public class SearchLogService {
//
//    private final SearchLogRepository searchLogRepository;
//    private final UserInfoService userInfoService;
//
//    @Transactional
//    public SearchLog saveSearchQuery(String searchQuery) {
//        // SecurityContext에서 로그인한 사용자 정보 가져오기
//        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
//
//        // 사용자 정보 조회 (Optional)
//        UserInfo userInfo = userInfoService.findByUsername(currentUsername).orElse(null); // nickname 또는 email로 조회
//
//        SearchLog searchLog = SearchLog.builder()
//                .userInfo(userInfo)  // 로그인한 사용자 정보 (없으면 null)
//                .searchQuery(searchQuery)
//                .searchCategory("")
//                .searchTime(LocalDateTime.now())
//                .build();
//
//        // 검색 로그 저장
//        searchLog = searchLogRepository.save(searchLog);
//
//        // search_category 업데이트 실행
//        searchLogRepository.updateSearchCategory(searchLog.getSearchLogId());
//
//        return searchLog;
//    }
//}

