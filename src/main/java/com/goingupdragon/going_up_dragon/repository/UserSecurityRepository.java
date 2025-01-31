package com.goingupdragon.going_up_dragon.repository;

import com.goingupdragon.going_up_dragon.entity.UserSecurity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSecurityRepository extends JpaRepository<UserSecurity, Long> {
}