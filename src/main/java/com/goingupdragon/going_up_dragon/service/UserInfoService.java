package com.goingupdragon.going_up_dragon.service;

//import com.goingupdragon.going_up_dragon.entity.UserInfo;
//import com.goingupdragon.going_up_dragon.repository.UserInfoRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//public class UserInfoService {
//
//    private final UserInfoRepository userInfoRepository;
//
//    public Optional<UserInfo> getLoggedInUser() {
//        // 현재 로그인한 사용자의 Authentication 가져오기
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        // 로그인하지 않았다면 null 반환
//        if (authentication == null || !authentication.isAuthenticated()) {
//            return Optional.empty();
//        }
//
//        Object principal = authentication.getPrincipal();
//
//        if (principal instanceof UserDetails userDetails) {
//            String username = userDetails.getUsername();
//            return userInfoRepository.findByNickname(username); // 닉네임 기준으로 조회
//        }
//
//        return Optional.empty();
//    }
//}

import com.goingupdragon.going_up_dragon.entity.UserInfo;
import com.goingupdragon.going_up_dragon.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserInfoService {

    private final UserInfoRepository userInfoRepository;

    public Optional<UserInfo> getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = ((User) authentication.getPrincipal()).getUsername();
            return userInfoRepository.findByNickname(username); // 예: username으로 UserInfo를 조회
        }
        return Optional.empty();
    }
}





