package com.goingupdragon.going_up_dragon.repository;

import com.goingupdragon.going_up_dragon.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

}
