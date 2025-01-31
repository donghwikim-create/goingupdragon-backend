package com.goingupdragon.going_up_dragon.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_info")
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long infoId;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")  // 'user_id' 컬럼을 참조
    private UserSecurity userSecurity;
    private String nickname;
    private String bio;
    private String profileImage;
    private String role;
    private String profileAddress;
}
