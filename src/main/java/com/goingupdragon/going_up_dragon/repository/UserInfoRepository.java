package com.goingupdragon.going_up_dragon.repository;

import com.goingupdragon.going_up_dragon.entity.UserInfo;
import com.goingupdragon.going_up_dragon.entity.UserSecurity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {
    Optional<UserInfo> findByNickname(String nickname); // nickName ❌ → nickname ✅
    Optional<UserInfo> findByUser(UserSecurity userSecurity);
}

