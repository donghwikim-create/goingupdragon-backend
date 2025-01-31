package com.goingupdragon.going_up_dragon.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_security")
public class UserSecurity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")  // 'user_id' 컬럼 정의
    private Long userId;

    @Column(name = "email", nullable = true, unique = true, length = 255)
    private String email;

    @Column(name = "password", nullable = true, length = 255)
    private String password;

    @Column(name = "sns_email", nullable = true, unique = true, length = 255)
    private String snsEmail;

    @Column(name = "signup_type", nullable = false)
    private String signupType;

    @Column(name = "join_date", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime joinDate = LocalDateTime.now();

    @Column(name = "last_login", nullable = true)
    private LocalDateTime lastLogin;

    @Column(name = "status", nullable = false)
    private String status = "active";

    @Column(name = "phone_number", nullable = false, length = 15)
    private String phoneNumber;
}

