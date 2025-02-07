//package com.goingupdragon.going_up_dragon.service;
//
//import com.goingupdragon.going_up_dragon.entity.SearchLog;
//import com.goingupdragon.going_up_dragon.entity.UserInfo;
//import com.goingupdragon.going_up_dragon.repository.SearchLogRepository;
//import com.goingupdragon.going_up_dragon.repository.UserInfoRepository;
//import lombok.RequiredArgsConstructor;
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
//        // 로그인한 사용자 정보 가져오기 (없으면 null)
//        UserInfo userInfo = userInfoService.getLoggedInUser().orElse(null);
//
//        // 2️⃣ SearchLog 객체 생성 및 저장
//        SearchLog searchLog = SearchLog.builder()
//                .userInfo(userInfo)  // 로그인한 사용자 정보 (없으면 null)
//                .searchQuery(searchQuery)
//                .searchCategory(null)
//                .searchTime(LocalDateTime.now())
//                .build();
//
//        searchLog = searchLogRepository.save(searchLog);
//
//        // search_category 업데이트 실행
//        searchLogRepository.updateSearchCategory(searchLog.getSearchLogId());
//
//        return searchLog;
//    }
//}
//
//

package com.goingupdragon.going_up_dragon.service;

import com.goingupdragon.going_up_dragon.entity.SearchLog;
import com.goingupdragon.going_up_dragon.entity.SearchLogElasticsearch;
import com.goingupdragon.going_up_dragon.entity.UserInfo;
import com.goingupdragon.going_up_dragon.repository.SearchLogRepository;
import com.goingupdragon.going_up_dragon.repository.SearchLogElasticsearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchLogService {

    private final SearchLogRepository searchLogRepository;
    private final SearchLogElasticsearchRepository searchLogElasticsearchRepository;  // 추가된 리포지토리
    private final UserInfoService userInfoService;

    @Transactional
    public SearchLog saveSearchQuery(String searchQuery) {
        // 로그인한 사용자 정보 가져오기 (없으면 null)
        UserInfo userInfo = userInfoService.getLoggedInUser().orElse(null);

        // 2️⃣ SearchLog 객체 생성 및 저장
        SearchLog searchLog = SearchLog.builder()
                .userInfo(userInfo)  // 로그인한 사용자 정보 (없으면 null)
                .searchQuery(searchQuery)
                .searchCategory(null)
                .searchTime(LocalDateTime.now())
                .build();

        searchLog = searchLogRepository.save(searchLog);

        // search_category 업데이트 실행
        searchLogRepository.updateSearchCategory(searchLog.getSearchLogId());

        // Elasticsearch에 저장
        saveToElasticsearch(searchLog);

        return searchLog;
    }

    // Elasticsearch에 데이터를 저장하는 메서드
    private void saveToElasticsearch(SearchLog searchLog) {
        SearchLogElasticsearch searchLogElasticsearch = new SearchLogElasticsearch();
        searchLogElasticsearch.setSearchQuery(searchLog.getSearchQuery());
        searchLogElasticsearch.setSearchCategory(searchLog.getSearchCategory());
        searchLogElasticsearch.setSearchTime(searchLog.getSearchTime());

        searchLogElasticsearchRepository.save(searchLogElasticsearch);
    }
}

